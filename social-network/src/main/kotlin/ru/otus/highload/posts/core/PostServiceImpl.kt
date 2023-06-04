package ru.otus.highload.posts.core

import org.springframework.stereotype.Component
import ru.otus.highload.common.exception.CommentNotFoundException
import ru.otus.highload.posts.core.model.PostDto
import ru.otus.highload.users.core.FriendsFacade
import java.time.LocalDateTime
import java.util.*

@Component
class PostServiceImpl(
        private val postPersistencePort: PostPersistencePort,
        private val postCacheProducer: PostCacheProducer,
        private val postCacheService: PostCacheService,
        private val subscriberNotifier: SubscriberNotifier,
        private val friendsFacade: FriendsFacade
) : PostService {

    override fun add(postText: String, userId: String): String {
        val post = PostDto(UUID.randomUUID().toString(), userId, postText)
        postPersistencePort.addPost(post)
        val friendIds = friendsFacade.getUsersByFriendId(userId)
        postCacheProducer.sendCreateEvent(userId, friendIds, post.copy(createDate = LocalDateTime.now(), modifyDate = LocalDateTime.now()))
        subscriberNotifier.sendNewPostToSubscribers(friendIds, post.copy(createDate = LocalDateTime.now(), modifyDate = LocalDateTime.now()))
        return post.postId;
    }

    override fun deleteByPostId(postId: String, userId: String) {
        postPersistencePort.deleteByPostId(postId)
        val friendIds = friendsFacade.getUsersByFriendId(userId)
        postCacheProducer.sendDeleteEvent(userId, friendIds, postId)
    }

    override fun deleteByUserId(userId: String) {
        postPersistencePort.deleteByUserId(userId)
    }

    override fun update(postText: String, postId: String, userId: String) {
        postPersistencePort.getByPostId(postId)?.let {
            val newPost = it.copy(postText = postText)
            postPersistencePort.updatePost(newPost)
            val friendIds = friendsFacade.getUsersByFriendId(userId)
            postCacheProducer.sendUpdateEvent(userId, friendIds, newPost.copy(modifyDate = LocalDateTime.now()))
        }
    }

    override fun get(postId: String): PostDto =
            postPersistencePort.getByPostId(postId)
                    ?: throw CommentNotFoundException("Пост с таким идентификатором не найден")

    override fun getByUserId(userId: String): List<PostDto> =
            postPersistencePort.getByUserId(userId)

    override fun friendFeed(offset: Long, number: Long, userId: String): List<PostDto> =
            postCacheService.getCache(userId, offset, number)
}
