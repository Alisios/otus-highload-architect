package ru.otus.highload.chats.core.model

import ru.otus.highload.chats.core.db.MessageDtoEntity
import java.time.LocalDateTime

data class MessageDto(
    val messageId: String,
    val fromUserId: String,
    val chatId: String,
    val toUserId: String,
    val text: String,
    val created: LocalDateTime? = null,
)

fun MessageDto.toEntity(): MessageDtoEntity = MessageDtoEntity(
    messageId = messageId,
    fromUserId = fromUserId,
    chatId = chatId,
    toUserId = toUserId,
    text = text,
    created = created
)

fun MessageDtoEntity.toDto(): MessageDto = MessageDto(
    messageId = messageId,
    fromUserId = fromUserId,
    chatId = chatId,
    toUserId = toUserId,
    text = text,
    created = created
)
