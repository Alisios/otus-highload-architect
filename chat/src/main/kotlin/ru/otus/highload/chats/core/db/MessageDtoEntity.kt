package ru.otus.highload.chats.core.db

import java.time.LocalDateTime

data class MessageDtoEntity(
    val messageId: String,
    val chatId: String,
    val fromUserId: String,
    val toUserId: String,
    val text: String,
    val created: LocalDateTime? = null,
)
