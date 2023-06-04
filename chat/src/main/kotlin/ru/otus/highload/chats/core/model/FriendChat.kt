package ru.otus.highload.chats.core.model

import ru.otus.highload.chats.core.db.FriendChatEntity
import java.time.LocalDateTime

data class FriendChat(
    val dialogId: String,
    val fromUserId: String,
    val toUserId: String,
    val messages: MutableList<MessageDto>,
    val created: LocalDateTime? = null
) : Chat

fun FriendChat.toEntity(): FriendChatEntity = FriendChatEntity(
    chatId = dialogId,
    fromUserId = fromUserId,
    toUserId = toUserId,
    created = created
)

fun FriendChatEntity.toDto(messages: MutableList<MessageDto>): FriendChat = FriendChat(
    dialogId = chatId,
    fromUserId = fromUserId,
    toUserId = toUserId,
    messages = messages,
    created = created
)
