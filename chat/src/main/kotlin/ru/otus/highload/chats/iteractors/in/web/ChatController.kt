package ru.otus.highload.chats.iteractors.`in`.web

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import ru.otus.highload.chats.core.ChatService
import ru.otus.highload.chats.core.model.FriendChat
import ru.otus.highload.chats.core.model.MessageDto
import ru.otus.highload.chats.core.model.UserMessageDto
import ru.otus.highload.common.DefaultErrorResponse

@Tag(name = "Контроллер чатов пользователя")
@RestController
@RequestMapping("/v2/chats")
@CrossOrigin
class ChatController(private val chatService: ChatService) {

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
            @RequestBody userMessageDto: UserMessageDto, @RequestHeader("userId") userId: String
    ) = chatService.sendMessage(userMessageDto, userId)

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
    fun getByUserId(@RequestHeader("userId") userId: String): List<FriendChat> =
            chatService.getAllChats(userId)

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
    fun getChatByChatId(@PathVariable("chatId") chatId: String, @RequestHeader("userId") userId: String): MutableList<MessageDto> =
            chatService.getChatByChatId(userId, chatId)
}