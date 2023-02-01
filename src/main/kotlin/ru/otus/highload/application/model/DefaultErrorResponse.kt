package ru.otus.highload.application.model

import io.swagger.v3.oas.annotations.media.Schema

@Schema(name = "Дто ошибки")
data class DefaultErrorResponse(
    @Schema(description = "Текст ошибки", example = "Пользователь не найден")
    val message: String
)
