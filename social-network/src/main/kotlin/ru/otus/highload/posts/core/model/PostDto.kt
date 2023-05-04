package ru.otus.highload.posts.core.model

import io.swagger.v3.oas.annotations.media.Schema
import ru.otus.highload.posts.core.db.persistence.PostEntity
import java.time.LocalDateTime
import javax.validation.constraints.NotEmpty

@Schema(name = "Дто поста")
data class PostDto(

    @field:Schema(description = "Идентификатор поста", example = "cfc893bf-cad9-4fa5-941f-d4627ee783e3")
    @field:NotEmpty(message = "Идентификатор поста не может быть пустым")
    val postId: String,

    @field:Schema(description = "Идентификатор пользователя", example = "cfc893bf-cad9-4fa5-941f-d4627ee783e3")
    @field:NotEmpty(message = "Идентификатор пользователя не может быть пустым")
    val userId: String,

    @field:Schema(description = "Текст поста", example = "Это просто бомба")
    @field:NotEmpty(message = "Текст поста не может быть пустым")
    val postText: String,

    @field:Schema(description = "Время создания поста", example = "2023-03-20T21:00:02.019")
    var createDate: LocalDateTime? = null,

    @field:Schema(description = "Время обновления поста", example = "2023-03-20T21:00:02.019")
    var modifyDate: LocalDateTime? = null
)

fun PostDto.toEntity() = PostEntity(
    postId = postId,
    userId = userId,
    postText = postText
)

fun PostEntity.toDto() = PostDto(
    postId = postId,
    userId = userId,
    postText = postText,
    modifyDate = modifyDate,
    createDate = createDate
)
