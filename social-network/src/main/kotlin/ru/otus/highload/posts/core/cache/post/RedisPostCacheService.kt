package ru.otus.highload.posts.core.cache.post

import mu.KLogging
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import ru.otus.highload.posts.config.cache.CacheSettings
import ru.otus.highload.posts.core.PostCacheService
import ru.otus.highload.posts.core.PostPersistencePort
import ru.otus.highload.posts.core.model.PostDto
import java.time.LocalDateTime

@Service
class RedisPostCacheService(
        private val postRedisRepository: PostRedisRepository,
        private val postPersistencePort: PostPersistencePort,
        private val cacheSettings: CacheSettings,
) : PostCacheService {

    private companion object : KLogging()

    override fun updatePostInCache(userId: String, postDto: PostDto) {
        postRedisRepository.findByIdOrNull(userId)?.let { cacheEntity ->
            logger.info { "[обновление поста] Закэшированная лента: $cacheEntity" }
            val postOld = cacheEntity.friendPosts.find { it.postId == postDto.postId }
            cacheEntity.friendPosts.remove(postOld)
            cacheEntity.friendPosts.add(postDto)
            cacheEntity.friendPosts.sortWith(compareByDescending { it.modifyDate })
            postRedisRepository.save(cacheEntity)
            logger.info { "[обновление поста] Обновленная лента: $cacheEntity" }
        }?: logger.warn { "Кеша для данного пользователя не найдено $userId" }
    }

    override fun updatePostFeedIfExists(userId: String) {
        postRedisRepository.findByIdOrNull(userId)?.let { cacheEntity ->
            postRedisRepository.save(cacheEntity.copy(friendPosts = postPersistencePort.getFriendsFeed(userId)))
            logger.info { "Успешное обновление кеша для пользователя $userId" }
        } ?: logger.warn { "Кеша для данного пользователя не найдено $userId" }
    }

    override fun addPostByUserId(userId: String, postDto: PostDto) {
        postRedisRepository.findByIdOrNull(userId)?.let { cacheEntity ->
            logger.info { "[Добавление поста] Закэшированная лента: $cacheEntity" }
            cacheEntity.friendPosts.add(postDto)
            cacheEntity.friendPosts.sortWith(compareByDescending { it.modifyDate })
            if (cacheEntity.friendPosts.size > cacheSettings.postsMaxNumberPerUser) cacheEntity.friendPosts.removeLast()
            postRedisRepository.save(cacheEntity)
            logger.info { "[Добавление поста] Обновленная лента: $cacheEntity" }
        } ?: addNewUserToCache(userId)
    }

    override fun addFriendPostsById(userId: String, friendId: String) {
        postRedisRepository.findByIdOrNull(userId)?.let { cacheEntity ->
            logger.info { "[Добавление друга] Закэшированная лента: $cacheEntity" }
            val postDtos = postPersistencePort.getByUserId(friendId)
            cacheEntity.friendPosts.addAll(postDtos)
            cacheEntity.friendPosts.sortWith(compareByDescending { it.modifyDate })
            if (cacheEntity.friendPosts.size > cacheSettings.postsMaxNumberPerUser)
                postRedisRepository.save(
                    cacheEntity.copy(
                        friendPosts =
                        cacheEntity.friendPosts.subList(0, cacheSettings.postsMaxNumberPerUser.toInt())
                    )
                )
            else postRedisRepository.save(cacheEntity)
            logger.info { "[Добавление друга] Обновленная лента: $cacheEntity" }
        } ?: addNewUserToCache(userId)
    }

    override fun getCache(userId: String, offset: Long, number: Long): List<PostDto> {
        return try {
            postRedisRepository.findByIdOrNull(userId)?.friendPosts ?: addNewUserToCache(userId, offset, number)
        } catch (ex: Exception) {
            logger.error("Ошибка при обращении к кэшу", ex)
            postPersistencePort.getFriendsFeed(userId, offset, number)
        }
    }

    override fun deleteCache(userId: String) {
        postRedisRepository.deleteById(userId)
        logger.info { "Успешное удаление кеша для пользователя $userId" }
    }

    private fun addNewUserToCache(userId: String, offset: Long? = null, number: Long? = null): List<PostDto> {
        val postsByUser = postPersistencePort.getFriendsFeed(userId, offset, number)
        postRedisRepository.save(PostFeedEntity(userId, postsByUser, LocalDateTime.now()))
        logger.info { "Добавление кеша для пользователя $userId" }
        return postsByUser
    }
}
