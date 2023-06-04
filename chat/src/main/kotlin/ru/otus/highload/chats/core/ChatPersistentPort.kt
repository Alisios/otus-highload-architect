package ru.otus.highload.chats.core

import ru.otus.highload.chats.core.model.FriendChat
import ru.otus.highload.chats.core.model.MessageDto

interface ChatPersistentPort {

    fun saveMessageToChat(message: MessageDto)

    fun getMessagesByChatId(chatId: String): MutableList<MessageDto>

    fun getAllChatsByUserId(userId: String): MutableList<FriendChat>
}
