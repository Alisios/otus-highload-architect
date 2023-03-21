package ru.otus.highload.domain.users.core.ports.`in`

import org.springframework.validation.annotation.Validated
import ru.otus.highload.domain.users.core.model.NewPostDto
import ru.otus.highload.domain.users.core.model.PostDto
import javax.validation.Valid
import javax.validation.constraints.Min
import javax.validation.constraints.NotEmpty

@Validated
interface PostQuery {

    fun add(@NotEmpty(message = "Текст поста не может быть пустым") postText: String, userId: String): String

    fun deleteByPostId(@NotEmpty(message = "Идентификатор поста не может быть пустым") postId: String)

    fun deleteByUserId(userId: String)

    fun update(postText: String, postId: String)

    fun get(@NotEmpty(message = "Идентификатор поста не может быть пустым") postId: String): PostDto

    fun getByUserId(userId: String): List<PostDto>

    fun feed(@Min(0) offset: Long = 0, number: Long = 10, userId: String): List<PostDto>
}
