package ru.otus.highload.domain.users.application.web

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import ru.otus.highload.application.model.DefaultErrorResponse
import ru.otus.highload.domain.users.core.model.PostDto
import ru.otus.highload.domain.users.core.ports.`in`.PostQuery

@Tag(name = "Контроллер постов пользователя")
@RestController
@CrossOrigin
class PostController(private val postQuery: PostQuery) {

    @Operation(summary = "Создание нового поста")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "201", description = "Успешно создан пост",
                content = [Content(schema = Schema(implementation = String::class))]
            ),
            ApiResponse(responseCode = "401", description = "Unauthorized", content = [Content()]),
            ApiResponse(
                responseCode = "400", description = "Некорректные данные",
                content = [Content(schema = Schema(implementation = DefaultErrorResponse::class))]
            ),
            ApiResponse(
                responseCode = "404", description = "Пользователь не найден",
                content = [Content(schema = Schema(implementation = DefaultErrorResponse::class))]
            ),
            ApiResponse(
                responseCode = "500", description = "Внутренняя ошибка",
                content = [Content(schema = Schema(implementation = DefaultErrorResponse::class))]
            )
        ]
    )
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/post/create")
    fun add(
        @RequestBody postText: String,
    ): String =
        postQuery.add(postText, SecurityContextHolder.getContext().authentication.name)

    @DeleteMapping("/post/delete/{id}")
    fun delete(
        @Parameter(
            example = "da380907-ea42-4985-8fc4-92d99eab39a8",
            required = true
        ) @PathVariable("id") postId: String
    ) = postQuery.deleteByPostId(postId)


    @PutMapping("/post/update/{id}")
    fun update(
        @Parameter(
            example = "da380907-ea42-4985-8fc4-92d99eab39a8",
            required = true
        ) @PathVariable("id") postId: String,
        @RequestBody postMessage: String
    ) = postQuery.update(postMessage, postId)

    @GetMapping("/post/get/{id}")
    fun get(
        @Parameter(
            example = "7965ba56-5505-40b4-9a94-a434883d7b7f",
            required = true
        ) @PathVariable("id") postId: String
    ): PostDto = postQuery.get(postId)

    @GetMapping("/post")
    fun getByUserId(): List<PostDto> =
        postQuery.getByUserId(SecurityContextHolder.getContext().authentication.name)

    @GetMapping("/post/feed")
    fun getFeed(): List<PostDto> =
        postQuery.feed(0, 10, SecurityContextHolder.getContext().authentication.name)

}
