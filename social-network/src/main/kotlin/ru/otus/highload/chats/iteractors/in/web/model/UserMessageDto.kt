package ru.otus.highload.chats.iteractors.`in`.web.model

data class UserMessageDto(
    val dialogId: String,
    val toUserId: String,
    val text: String
)
