package ru.otus.highload.application.model

import io.swagger.v3.oas.annotations.media.Schema
import ru.otus.highload.infrastructure.AdditionalLocale
import java.util.Locale

@Schema(name = "Дто ошибки")
data class DefaultErrorResponse(
    @Schema(description = "Текст ошибки", example = "Пользователь не найден")
    val message: String
)

@Suppress("ClassName")
class defaultError(val locale: Locale)

class LocalizedErrorResponsePart(val locale: Locale, val messageRu: String)

infix fun defaultError.ru(messageRu: String) = LocalizedErrorResponsePart(locale, messageRu)

infix fun LocalizedErrorResponsePart.en(messageEn: String): DefaultErrorResponse = when (locale) {
    Locale.ENGLISH -> DefaultErrorResponse(messageEn)
    AdditionalLocale.RUSSIAN -> DefaultErrorResponse(messageRu)
    else -> DefaultErrorResponse(messageRu)
}
