package ru.otus.highload.chats.core

import mu.KLogging
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.MediaType
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import org.springframework.web.util.UriComponentsBuilder
import reactor.core.publisher.Mono
import ru.otus.highload.chats.core.config.ChatSettings
import ru.otus.highload.chats.iteractors.`in`.web.model.FriendChat
import ru.otus.highload.chats.iteractors.`in`.web.model.MessageDto
import ru.otus.highload.chats.iteractors.`in`.web.model.UserMessageDto
import ru.otus.highload.common.exception.ChatServiceException

@Service
class ChatAdapterRest(
        private val chatWebClient: WebClient,
        private val chatSettings: ChatSettings
) : ChatAdapter {

    private companion object : KLogging() {
        const val USER_ID_HEADER = "userId"
    }

    private fun createHeaders(userId: String): HttpHeaders {
        val headers = HttpHeaders()
        headers.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
        headers.set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
        headers.set(USER_ID_HEADER, userId)
        return headers
    }


    override fun getChatByChatId(userId: String, chatId: String): MutableList<MessageDto> {
        val uriComponents = UriComponentsBuilder.fromHttpUrl(chatSettings.getChatByChatId).build()
        val entity = HttpEntity<Any>(createHeaders(userId))
        val response = restTemplate
                .exchange(uriComponents.toUriString(), HttpMethod.GET, entity, Limits::class.java)
        return response.body!!
    }

    override fun getAllChats(userId: String): List<FriendChat> {
        val response = runCatching {
            chatWebClient
                    .post()
                    .uri(chatSettings.getChatUrl)
                    .body(Mono.just(feeRequest), FeeRequestDto::class.java)
                    .retrieve().bodyToMono(FeeResponseDto::class.java).block()
        }.fold(
                onSuccess = {
                    it
                },
                onFailure = { thr ->
                    throw ChatServiceException("Failed to receive all chats for client $userId").also {
                        logger.error {
                            "Failed to receive all chats for client $userId. Error:  ${thr.message}; Cause:  ${thr.cause}; Stack: ${thr.stackTraceToString()}"
                        }
                    }
                }
        )
    }

    override fun sendMessage(userMessageDto: UserMessageDto, userId: String) {
        TODO("Not yet implemented")
    }
}
