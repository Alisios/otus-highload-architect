package ru.otus.highload.application

import mu.KLogging
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.validation.FieldError
import org.springframework.validation.ObjectError
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import ru.otus.highload.application.model.DefaultErrorResponse
import ru.otus.highload.application.model.defaultError
import ru.otus.highload.application.model.en
import ru.otus.highload.application.model.ru
import ru.otus.highload.domain.commmon.exceptions.AuthenticationException
import ru.otus.highload.domain.commmon.exceptions.ExistedUserException
import ru.otus.highload.domain.commmon.exceptions.NotFoundUserException
import java.util.*
import java.util.function.Consumer


@ControllerAdvice
class ExceptionHandleController {

    private companion object : KLogging() {
        const val DEFAULT_ERROR_MESSAGE_RU = "Что-то пошло не так. Попробуйте позже"
        const val DEFAULT_ERROR_MESSAGE_EN = "Something went wrong. Try it later"

        const val NOT_FOUND_ERROR_MESSAGE_RU = "Пользователь  не найден"
        const val NOT_ERROR_MESSAGE_EN = "User is not found"

        const val NOT_FOUND_LOGIN_ERROR_MESSAGE_RU = "Пользователь c таким логином не найден"
        const val NOT_FOUND_LOGIN_ERROR_MESSAGE_EN = "User with this login is not found"
    }

    @ExceptionHandler(Exception::class)
    fun defaultHandler(e: Exception, locale: Locale): ResponseEntity<DefaultErrorResponse> =
        ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .contentType(MediaType.APPLICATION_JSON)
            .body(defaultError(locale) ru DEFAULT_ERROR_MESSAGE_RU en DEFAULT_ERROR_MESSAGE_EN)
            .also { logger.error(e) { "Server error: ${e.message}" } }

    @ExceptionHandler(NotFoundUserException::class)
    fun notFoundExceptionHandler(e: Exception, locale: Locale): ResponseEntity<DefaultErrorResponse> =
        ResponseEntity.status(HttpStatus.NOT_FOUND)
            .contentType(MediaType.APPLICATION_JSON)
            .body(defaultError(locale) ru NOT_FOUND_ERROR_MESSAGE_RU en NOT_ERROR_MESSAGE_EN)
            .also { logger.error(e) { "Not found error: ${e.message}" } }

    @ExceptionHandler(AuthenticationException::class)
    fun authenticationExceptionsHandler(e: Exception, locale: Locale): ResponseEntity<DefaultErrorResponse> =
        ResponseEntity.status(HttpStatus.UNAUTHORIZED)
            .contentType(MediaType.APPLICATION_JSON)
            .body(e.message?.let { DefaultErrorResponse(it) })
            .also { logger.error(e) { "Authentication: ${e.message}" } }

    @ExceptionHandler(ExistedUserException::class, HttpMessageNotReadableException::class)
    fun existedUserExceptionsHandler(e: Exception, locale: Locale): ResponseEntity<DefaultErrorResponse> =
        ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .contentType(MediaType.APPLICATION_JSON)
            .body(e.message?.let { DefaultErrorResponse(it) })
            .also { logger.error(e) { "Bad request: ${e.message}" } }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleValidationExceptions(
        ex: MethodArgumentNotValidException
    ): Map<String, String>? {
        val errors: MutableMap<String, String> = HashMap()
        ex.bindingResult.allErrors.forEach(Consumer { error: ObjectError ->
            val fieldName = (error as FieldError).field
            val errorMessage = error.getDefaultMessage()
            if (errorMessage != null) {
                errors[fieldName] = errorMessage
            }
        })
        return errors
    }
}
