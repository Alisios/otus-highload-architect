package ru.otus.highload.chats.core.model

data class UserMessageDto(
    val dialogId: String,
    val toUserId: String,
    val text: String
)
