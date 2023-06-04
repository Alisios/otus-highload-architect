package ru.otus.highload.posts.iteractors.`in`.websocket

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestParam
import ru.otus.highload.common.DefaultErrorResponse
import ru.otus.highload.posts.core.PostService
import ru.otus.highload.posts.core.model.PostDto

@Controller
class FriendPostFeedWebSocketController(private val postService: PostService) {

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
    @MessageMapping("/feed")
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
