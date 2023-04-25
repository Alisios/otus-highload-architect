package ru.otus.highload.posts.iteractors.`in`.web

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
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import ru.otus.highload.common.DefaultErrorResponse
import ru.otus.highload.posts.core.model.PostDto
import ru.otus.highload.posts.core.PostService

@Tag(name = "Контроллер постов пользователя")
@RestController
@RequestMapping("/posts")
@CrossOrigin
class PostController(private val postService: PostService) {

    @Operation(summary = "Создание нового поста")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "201", description = "Успешно создан пост"
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
    @PostMapping
    fun addPost(
        @RequestBody postText: String,
    ): String =
        postService.add(postText, SecurityContextHolder.getContext().authentication.name)


    @Operation(summary = "Удаление поста")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200", description = "Пост удален"
            ),
            ApiResponse(responseCode = "401", description = "Unauthorized", content = [Content()]),
            ApiResponse(
                responseCode = "400", description = "Некорректные данные",
                content = [Content(schema = Schema(implementation = DefaultErrorResponse::class))]
            ),
            ApiResponse(
                responseCode = "404", description = "Пост не найден",
                content = [Content(schema = Schema(implementation = DefaultErrorResponse::class))]
            ),
            ApiResponse(
                responseCode = "500", description = "Внутренняя ошибка",
                content = [Content(schema = Schema(implementation = DefaultErrorResponse::class))]
            )
        ]
    )
    @DeleteMapping("/{id}")
    fun deletePostById(
        @Parameter(
            example = "da380907-ea42-4985-8fc4-92d99eab39a8",
            required = true
        ) @PathVariable("id") postId: String
    ) = postService.deleteByPostId(postId, SecurityContextHolder.getContext().authentication.name)


    @Operation(summary = "Обновление поста")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200", description = "Пост обновлен"
            ),
            ApiResponse(responseCode = "401", description = "Unauthorized", content = [Content()]),
            ApiResponse(
                responseCode = "400", description = "Некорректные данные",
                content = [Content(schema = Schema(implementation = DefaultErrorResponse::class))]
            ),
            ApiResponse(
                responseCode = "404", description = "Пост не найден",
                content = [Content(schema = Schema(implementation = DefaultErrorResponse::class))]
            ),
            ApiResponse(
                responseCode = "500", description = "Внутренняя ошибка",
                content = [Content(schema = Schema(implementation = DefaultErrorResponse::class))]
            )
        ]
    )
    @PutMapping("{id}")
    fun updatePostById(
        @Parameter(
            example = "da380907-ea42-4985-8fc4-92d99eab39a8",
            required = true
        ) @PathVariable("id") postId: String,
        @RequestBody postMessage: String
    ) = postService.update(postMessage, postId, SecurityContextHolder.getContext().authentication.name)


    @Operation(summary = "Получение поста по идентификатору")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200", description = "Ок"
            ),
            ApiResponse(responseCode = "401", description = "Unauthorized", content = [Content()]),
            ApiResponse(
                responseCode = "400", description = "Некорректные данные",
                content = [Content(schema = Schema(implementation = DefaultErrorResponse::class))]
            ),
            ApiResponse(
                responseCode = "404", description = "Пост не найден",
                content = [Content(schema = Schema(implementation = DefaultErrorResponse::class))]
            ),
            ApiResponse(
                responseCode = "500", description = "Внутренняя ошибка",
                content = [Content(schema = Schema(implementation = DefaultErrorResponse::class))]
            )
        ]
    )
    @GetMapping("{id}")
    fun getPostById(
        @Parameter(
            example = "7965ba56-5505-40b4-9a94-a434883d7b7f",
            required = true
        ) @PathVariable("id") postId: String
    ): PostDto = postService.get(postId)

    @Operation(summary = "Получение всех постов пользователя")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200", description = "Ок"
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
    @GetMapping("/user")
    fun getByUserId(): List<PostDto> =
        postService.getByUserId(SecurityContextHolder.getContext().authentication.name)


    @Operation(summary = "Получение постов друзей пользователя")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200", description = "Ок"
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
    @GetMapping("/feed")
    fun getFeed(
        @Parameter(
            description = "Оффсет с которого начинать выдачу",
            example = "0",
            required = false
        )
        @RequestParam("offset", required = false) offset: Long = 0,
        @Parameter(
            description = "Лимит, ограничивающий кол-во возвращенных сущностей",
            example = "1000",
            required = false
        )
        @RequestParam("limit", required = false) limit: Long = 1000
    ): List<PostDto> =
        postService.friendFeed(offset, limit, SecurityContextHolder.getContext().authentication.name)
}
