package ru.otus.highload.chats.core.db

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional
import ru.otus.highload.chats.core.ChatPersistentPort
import ru.otus.highload.chats.core.model.FriendChat
import ru.otus.highload.chats.core.model.MessageDto
import ru.otus.highload.chats.core.model.toDto
import ru.otus.highload.chats.core.model.toEntity

@Transactional
@Service
class ChatPersistentAdapter(private val chatRepository: ChatRepository) : ChatPersistentPort {

    override fun saveMessageToChat(message: MessageDto) {
        if (chatRepository.ifChatExist(message.chatId))
            chatRepository.saveMessage(message.toEntity())
        else {
            chatRepository.saveChat(FriendChatEntity(message.chatId, message.fromUserId, message.toUserId))
            chatRepository.saveMessage(message.toEntity())
        }
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    override fun getMessagesByChatId(chatId: String): MutableList<MessageDto> {
        return chatRepository.getMessagesByChatId(chatId).map { it.toDto() }.toMutableList()
    }


    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    override fun getAllChatsByUserId(userId: String): MutableList<FriendChat> =
        chatRepository.getAllChatsByUserId(userId).map { it.toDto(mutableListOf()) }.toMutableList()
}
