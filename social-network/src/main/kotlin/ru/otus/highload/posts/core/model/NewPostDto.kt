package ru.otus.highload.posts.core.model

import io.swagger.v3.oas.annotations.media.Schema
import javax.validation.constraints.NotEmpty

@Schema(name = "Дто поста")
data class NewPostDto(

    @field:Schema(description = "Идентификатор поста", example = "cfc893bf-cad9-4fa5-941f-d4627ee783e3")
    @field:NotEmpty(message = "Идентификатор поста не может быть пустым")
    val postId: String,

    @field:Schema(description = "Идентификатор пользователя", example = "cfc893bf-cad9-4fa5-941f-d4627ee783e3")
    @field:NotEmpty(message = "Текст поста не может быть пустым")
    val postText: String
)
