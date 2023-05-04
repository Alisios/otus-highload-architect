package ru.otus.highload.posts.core

import org.springframework.stereotype.Component
import ru.otus.highload.common.exception.CommentNotFoundException
import ru.otus.highload.posts.core.model.PostDto
import java.time.LocalDateTime
import java.util.*

@Component
class PostServiceImpl(
    private val postPersistencePort: PostPersistencePort,
    private val postProducer: PostProducer,
    private val postCacheService: PostCacheService
) : PostService {

    override fun add(postText: String, userId: String): String {
        val post = PostDto(UUID.randomUUID().toString(), userId, postText)
        postPersistencePort.addPost(post)
        postProducer.sendCreateEvent(
            userId, post.copy(createDate = LocalDateTime.now(), modifyDate = LocalDateTime.now()))
            return post.postId;
    }

    override fun deleteByPostId(postId: String, userId: String) {
        postPersistencePort.deleteByPostId(postId)
        postProducer.sendDeleteEvent(userId, postId)
    }

    override fun deleteByUserId(userId: String) {
        postPersistencePort.deleteByUserId(userId)
    }

    override fun update(postText: String, postId: String, userId: String) {
        postPersistencePort.getByPostId(postId)?.let {
            val newPost = it.copy(postText = postText)
            postPersistencePort.updatePost(newPost)
            postProducer.sendUpdateEvent(userId, newPost.copy(modifyDate = LocalDateTime.now()))
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
