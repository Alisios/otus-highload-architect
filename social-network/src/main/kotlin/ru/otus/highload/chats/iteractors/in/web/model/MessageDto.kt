package ru.otus.highload.chats.iteractors.`in`.web.model

import java.time.LocalDateTime

data class MessageDto(
    val messageId: String,
    val fromUserId: String,
    val chatId: String,
    val toUserId: String,
    val text: String,
    val created: LocalDateTime? = null,
)
