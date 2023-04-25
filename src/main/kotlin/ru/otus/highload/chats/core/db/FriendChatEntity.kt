package ru.otus.highload.chats.core.db

import java.time.LocalDateTime

data class FriendChatEntity(
    val chatId: String,
    val fromUserId: String,
    val toUserId: String,
    val created: LocalDateTime? = null
)
