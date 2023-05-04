package ru.otus.highload.chats.iteractors.`in`.web.model

data class MessageEvent(
    val dialogId: String,
    val messageDto: MessageDto
)
