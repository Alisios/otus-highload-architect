package ru.otus.highload.chats.core.db

interface ChatRepository {

    fun saveMessage(message: MessageDtoEntity)

    fun saveChat(friendChatEntity: FriendChatEntity)

    fun ifChatExist(chatId: String): Boolean

    fun getChatByChatId(chatId: String): FriendChatEntity?

    fun getMessagesByChatId(chatId: String): MutableList<MessageDtoEntity>

    fun getAllChatsByUserId(userId: String): MutableList<FriendChatEntity>
}
