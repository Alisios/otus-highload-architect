package ru.otus.highload.chats.core

import ru.otus.highload.chats.core.model.FriendChat
import ru.otus.highload.chats.core.model.MessageDto
import ru.otus.highload.chats.core.model.UserMessageDto

interface ChatService {

    fun getChatByChatId(userId: String, chatId: String): MutableList<MessageDto>

    fun getAllChats(userId: String): List<FriendChat>

    fun sendMessage(userMessageDto: UserMessageDto, userId: String)
}
