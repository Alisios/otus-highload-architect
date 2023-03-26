package ru.otus.highload.posts.core

import org.springframework.validation.annotation.Validated
import ru.otus.highload.posts.core.model.PostDto
import javax.validation.constraints.Min
import javax.validation.constraints.NotEmpty

@Validated
interface PostService {

    fun add(
        @NotEmpty(message = "Текст поста не может быть пустым") postText: String,
        @NotEmpty userId: String
    ): String

    fun deleteByPostId(
        @NotEmpty(message = "Идентификатор поста не может быть пустым") postId: String,
        @NotEmpty userId: String
    )

    fun deleteByUserId(@NotEmpty userId: String)

    fun update(
        @NotEmpty(message = "Текст поста не может быть пустым") postText: String,
        @NotEmpty(message = "Идентификатор поста не может быть пустым") postId: String,
        @NotEmpty userId: String
    )

    fun get(@NotEmpty(message = "Идентификатор поста не может быть пустым") postId: String): PostDto

    fun getByUserId(@NotEmpty userId: String): List<PostDto>

    fun friendFeed(@Min(0) offset: Long = 0, @Min(0) number: Long = 1000, @NotEmpty userId: String): List<PostDto>
}
