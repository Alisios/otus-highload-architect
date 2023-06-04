package ru.otus.highload.chats.core.model

data class MessageEvent(
    val dialogId: String,
    val messageDto: MessageDto
)
