package ru.otus.highload.common

import mu.KLogging
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import ru.otus.highload.common.exception.AuthenticationException
import ru.otus.highload.common.exception.CommentNotFoundException
import ru.otus.highload.common.exception.ExistedUserException
import ru.otus.highload.common.exception.NotFoundUserException
import java.util.*
import java.util.function.*
import javax.validation.ValidationException


@ControllerAdvice
class ExceptionHandleController {

    private companion object : KLogging() {
        const val DEFAULT_ERROR_MESSAGE_RU = "Что-то пошло не так. Попробуйте позже"
        const val NOT_FOUND_ERROR_MESSAGE_RU = "Информация не найдена"
    }

    @ExceptionHandler(Exception::class)
    fun defaultHandler(e: Exception): ResponseEntity<DefaultErrorResponse> =
        ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .contentType(MediaType.APPLICATION_JSON)
            .body(DefaultErrorResponse(DEFAULT_ERROR_MESSAGE_RU))
            .also { logger.error(e) { "Server error: ${e.message}" } }

    @ExceptionHandler(NotFoundUserException::class, CommentNotFoundException::class)
    fun notFoundExceptionHandler(e: Exception): ResponseEntity<DefaultErrorResponse> =
        ResponseEntity.status(HttpStatus.NOT_FOUND)
            .contentType(MediaType.APPLICATION_JSON)
            .body(DefaultErrorResponse(NOT_FOUND_ERROR_MESSAGE_RU))
            .also { logger.error(e) { "Not found error: ${e.message}" } }

    @ExceptionHandler(AuthenticationException::class)
    fun authenticationExceptionsHandler(
        e: Exception
    ): ResponseEntity<DefaultErrorResponse> =
        ResponseEntity.status(HttpStatus.UNAUTHORIZED)
            .contentType(MediaType.APPLICATION_JSON)
            .body(e.message?.let { DefaultErrorResponse(it) })
            .also { logger.error(e) { "Authentication error: ${e.message}" } }

    @ExceptionHandler(ExistedUserException::class, HttpMessageNotReadableException::class)
    fun existedUserExceptionsHandler(e: Exception): ResponseEntity<DefaultErrorResponse> =
        ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .contentType(MediaType.APPLICATION_JSON)
            .body(e.message?.let { DefaultErrorResponse(it) })
            .also { logger.error(e) { "Bad request: ${e.message}" } }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ValidationException::class)
    fun handleValidationExceptions(
        e: ValidationException
    ): ResponseEntity<DefaultErrorResponse> =
        ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .contentType(MediaType.APPLICATION_JSON)
            .body(e.message?.let { DefaultErrorResponse(it) })
            .also { logger.error(e) { "Bad request: ${e.message}" } }

}
