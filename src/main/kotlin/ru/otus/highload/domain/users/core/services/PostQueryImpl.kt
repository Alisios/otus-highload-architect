package ru.otus.highload.domain.users.core.services

import org.springframework.stereotype.Component
import ru.otus.highload.domain.commmon.exceptions.CommentNotFoundException
import ru.otus.highload.domain.commmon.exceptions.NotFoundUserException
import ru.otus.highload.domain.users.core.model.NewPostDto
import ru.otus.highload.domain.users.core.model.PostDto
import ru.otus.highload.domain.users.core.ports.`in`.PostQuery
import ru.otus.highload.domain.users.core.ports.out.PostPersistencePort
import ru.otus.highload.domain.users.core.ports.out.UserPersistencePort
import java.util.UUID

@Component
class PostQueryImpl(private val postPersistencePort: PostPersistencePort) : PostQuery {

    override fun add(postText: String, userId: String): String {
        val postId = UUID.randomUUID().toString();
        postPersistencePort.addPost(PostDto(postId, userId, postText))
        return postId;
    }

    override fun deleteByPostId(postId: String) {
        postPersistencePort.deleteByPostId(postId)
    }

    override fun deleteByUserId(userId: String) {
        postPersistencePort.deleteByUserId(userId)
    }

    override fun update(postText: String, postId: String) {
        postPersistencePort.getByPostId(postId)?.let {
            postPersistencePort.updatePost(it.copy(postText = postText))
        }
    }

    override fun get(postId: String): PostDto =
        postPersistencePort.getByPostId(postId)
            ?: throw CommentNotFoundException("Пост с таким идентификатором не найден")


    override fun getByUserId(userId: String): List<PostDto> =
        postPersistencePort.getByUserId(userId)


    override fun feed(offset: Long, number: Long, userId: String): List<PostDto> =
        postPersistencePort.getFriendsFeed(userId, offset, number)
}
