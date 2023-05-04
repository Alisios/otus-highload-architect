package ru.otus.highload.chats.core

import ru.otus.highload.chats.iteractors.`in`.web.model.FriendChat
import ru.otus.highload.chats.iteractors.`in`.web.model.MessageDto
import ru.otus.highload.chats.iteractors.`in`.web.model.UserMessageDto

interface ChatAdapter {

    fun getChatByChatId(userId: String, chatId: String): MutableList<MessageDto>

    fun getAllChats(userId: String): List<FriendChat>

    fun sendMessage(userMessageDto: UserMessageDto, userId: String)
}
