package ru.otus.highload.chats.core

import mu.KLogging
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.MediaType
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.BodyInserters
import org.springframework.web.reactive.function.client.WebClient
import ru.otus.highload.chats.core.config.ChatSettings
import ru.otus.highload.chats.iteractors.`in`.web.model.FriendChat
import ru.otus.highload.chats.iteractors.`in`.web.model.MessageDto
import ru.otus.highload.chats.iteractors.`in`.web.model.UserMessageDto
import ru.otus.highload.common.exception.ChatServiceException
import ru.otus.highload.configuration.USER_ID_HEADER

@Service
class ChatAdapterRest(
        private val chatWebClient: WebClient,
        private val chatSettings: ChatSettings
) : ChatAdapter {

    companion object : KLogging() {
        private const val CHAT_ID = "/{chatId}"
    }

    override fun getChatByChatId(userId: String, chatId: String): List<MessageDto> {
        try {
            return chatWebClient
                    .get()
                    .uri { it.path(CHAT_ID).build(chatId) }
                    .accept(MediaType.APPLICATION_JSON)
                    .header(USER_ID_HEADER, userId)
                    .retrieve()
                    .bodyToMono(object : ParameterizedTypeReference<MutableList<MessageDto>>() {})
                    .log()
                    .block()
                    .orEmpty()
                    .also { logger.info { "User $userId got chat by id $chatId (v1)" } }
        } catch (ex: Exception) {
            throw ChatServiceException("Failed to receive chat by id $chatId for user $userId", ex)
        }
    }

    override fun getAllChats(userId: String): List<FriendChat> {
        try {
            return chatWebClient
                    .get()
                    .uri(chatSettings.userChatSuffixUrl)
                    .accept(MediaType.APPLICATION_JSON)
                    .header(USER_ID_HEADER, userId)
                    .retrieve()
                    .bodyToMono(object : ParameterizedTypeReference<MutableList<FriendChat>>() {})
                    .log()
                    .block()
                    .orEmpty()
                    .also { logger.info { "User $userId got all chats (v1)" } }
        } catch (ex: Exception) {
            throw ChatServiceException("Failed to receive all chats for user $userId", ex)
        }
    }

    override fun sendMessage(userMessageDto: UserMessageDto, userId: String) {
        try {
            chatWebClient
                    .post()
                    .contentType(MediaType.APPLICATION_JSON)
                    .header(USER_ID_HEADER, userId)
                    .body(BodyInserters.fromValue(userMessageDto))
                    .retrieve()
                    .bodyToMono(Void::class.java)
                    .block().also { logger.info { "User $userId sent message $userMessageDto (v1)" } }
        } catch (ex: Exception) {
            throw ChatServiceException("Failed to send message ${userMessageDto} by user $userId", ex)
        }
    }
}
