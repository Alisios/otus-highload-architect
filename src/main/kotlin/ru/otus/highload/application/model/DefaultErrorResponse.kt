package ru.otus.highload.application.model

import ru.otus.highload.infrastructure.AdditionalLocale
import java.util.Locale

data class DefaultErrorResponse(val message: String)

@Suppress("ClassName")
class defaultError(val locale: Locale)

class LocalizedErrorResponsePart(val locale: Locale, val messageRu: String)

infix fun defaultError.ru(messageRu: String) = LocalizedErrorResponsePart(locale, messageRu)

infix fun LocalizedErrorResponsePart.en(messageEn: String): DefaultErrorResponse = when (locale) {
    Locale.ENGLISH -> DefaultErrorResponse(messageEn)
    AdditionalLocale.RUSSIAN -> DefaultErrorResponse(messageRu)
    else -> DefaultErrorResponse(messageRu)
}
