package ru.otus.highload.chats.iteractors.`in`.web.model

import java.time.LocalDateTime

data class FriendChat(
        val dialogId: String,
        val fromUserId: String,
        val toUserId: String,
        val messages: MutableList<MessageDto>,
        val created: LocalDateTime? = null
) : Chat

