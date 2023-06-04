package ru.otus.highload.chats.iteractors.`in`.web

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import ru.otus.highload.chats.core.ChatAdapter
import ru.otus.highload.chats.iteractors.`in`.web.model.FriendChat
import ru.otus.highload.chats.iteractors.`in`.web.model.MessageDto
import ru.otus.highload.chats.iteractors.`in`.web.model.UserMessageDto
import ru.otus.highload.common.DefaultErrorResponse

@Tag(name = "Контроллер чатов пользователя")
@RestController
@RequestMapping("/v1/chats")
@CrossOrigin
class ChatController(private val chatAdapter: ChatAdapter) {

    @Operation(summary = "Отправка нового сообщения")
    @ApiResponses(
            value = [
                ApiResponse(
                        responseCode = "201", description = "Сообщение успешно отправлено"
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
    fun sendMessage(
            @RequestBody userMessageDto: UserMessageDto,
    ) = chatAdapter.sendMessage(userMessageDto, SecurityContextHolder.getContext().authentication.name)

    @Operation(summary = "Получение всех чатов пользователя")
    @ApiResponses(
            value = [
                ApiResponse(
                        responseCode = "200", description = "Ок",
                        content = [Content(schema = Schema(implementation = FriendChat::class))]
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
    fun getByUserId(): List<FriendChat> =
            chatAdapter.getAllChats(SecurityContextHolder.getContext().authentication.name)

    @Operation(summary = "Получение всех чатов пользователя")
    @ApiResponses(
            value = [
                ApiResponse(
                        responseCode = "200", description = "Ок",
                        content = [Content(schema = Schema(implementation = MessageDto::class))]
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
    @GetMapping("/{chatId}")
    fun getChatByChatId(@PathVariable("chatId") chatId: String): List<MessageDto> =
            chatAdapter.getChatByChatId(SecurityContextHolder.getContext().authentication.name, chatId)
}
