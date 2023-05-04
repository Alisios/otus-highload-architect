package ru.otus.highload.chats.core

import org.springframework.stereotype.Service
import ru.otus.highload.chats.core.model.FriendChat
import ru.otus.highload.chats.core.model.MessageDto
import ru.otus.highload.chats.core.model.UserMessageDto
import ru.otus.highload.common.exception.NotFoundChatException
import java.util.UUID

@Service
class ChatServiceImpl(
    private val chatPersistentPort: ChatPersistentPort,
    private val messageProducer: MessageProducer
) : ChatService {

    override fun getChatByChatId(userId: String, chatId: String): MutableList<MessageDto> =
        chatPersistentPort.getMessagesByChatId(chatId)

    override fun getAllChats(userId: String): MutableList<FriendChat> =
        chatPersistentPort.getAllChatsByUserId(userId)


    override fun sendMessage(userMessageDto: UserMessageDto, userId: String) {
        val message = MessageDto(
            UUID.randomUUID().toString(),
            userId,
            userMessageDto.dialogId,
            userMessageDto.toUserId,
            userMessageDto.text
        )
        chatPersistentPort.saveMessageToChat(message)
        messageProducer.send(message)
    }
}
