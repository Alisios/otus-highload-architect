package ru.otus.highload.users.iteractors.`in`.web

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
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import ru.otus.highload.common.DefaultErrorResponse
import ru.otus.highload.users.core.FriendPersistentPort
import ru.otus.highload.users.core.FriendService
import ru.otus.highload.users.core.model.FriendDto

@Tag(name = "Добавление/удаление друга пользователя")
@RestController
@RequestMapping("/friends")
@CrossOrigin
class FriendController(private val friendService: FriendService) {

    @Operation(summary = "Добавление нового друга")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "201", description = "Друг добавлен"
            ),
            ApiResponse(responseCode = "401", description = "Unauthorized", content = [Content()]),
            ApiResponse(
                responseCode = "400", description = "Некорректные данные",
                content = [Content(schema = Schema(implementation = DefaultErrorResponse::class))]
            ),
            ApiResponse(
                responseCode = "404", description = "Не найдено",
                content = [Content(schema = Schema(implementation = DefaultErrorResponse::class))]
            ),
            ApiResponse(
                responseCode = "500", description = "Внутренняя ошибка",
                content = [Content(schema = Schema(implementation = DefaultErrorResponse::class))]
            )
        ]
    )
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("{friendId}")
    fun addFriend(
        @Parameter(
            description = "Идентификатор друга",
            required = true,
            example = "cefdd619-98eb-417b-a6ac-d2b196b0650b"
        )
        @PathVariable("friendId") friendId: String) =
        friendService.addFriend(friendId, SecurityContextHolder.getContext().authentication.name)


    @Operation(summary = "Удаление друга")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200", description = "Друг удален"
            ),
            ApiResponse(responseCode = "401", description = "Unauthorized", content = [Content()]),
            ApiResponse(
                responseCode = "400", description = "Некорректные данные",
                content = [Content(schema = Schema(implementation = DefaultErrorResponse::class))]
            ),
            ApiResponse(
                responseCode = "404", description = "Не найдено",
                content = [Content(schema = Schema(implementation = DefaultErrorResponse::class))]
            ),
            ApiResponse(
                responseCode = "500", description = "Внутренняя ошибка",
                content = [Content(schema = Schema(implementation = DefaultErrorResponse::class))]
            )
        ]
    )
    @DeleteMapping("{friendId}")
    fun deleteFriend(
        @Parameter(
            description = "Идентификатор друга",
            required = true,
            example = "cefdd619-98eb-417b-a6ac-d2b196b0650b"
        )
        @PathVariable("friendId") friendId: String) =
        friendService.deleteFriend(friendId, SecurityContextHolder.getContext().authentication.name)

    @Operation(summary = "Получение всех друзей")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200", description = "Друзья получены"
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
    @GetMapping()
    fun getAllFriends(): List<FriendDto> =
        friendService.getAllFriends(SecurityContextHolder.getContext().authentication.name)
}
