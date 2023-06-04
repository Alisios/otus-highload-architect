package ru.otus.highload.posts.iteractors.`in`.websocket

import mu.KLogging
import org.springframework.messaging.Message
import org.springframework.messaging.MessageChannel
import org.springframework.messaging.simp.stomp.StompCommand
import org.springframework.messaging.simp.stomp.StompHeaderAccessor
import org.springframework.messaging.support.ChannelInterceptor
import org.springframework.messaging.support.MessageHeaderAccessor
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Component
import ru.otus.highload.common.exception.AuthenticationException
import ru.otus.highload.posts.config.ws.USER_ID_HEADER
import java.security.Principal


@Component
class AuthChannelWsInterceptor() : ChannelInterceptor {

    private companion object : KLogging()

    override fun preSend(message: Message<*>, channel: MessageChannel): Message<*> {
        val accessor: StompHeaderAccessor? = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor::class.java)
        accessor?.let {
            if (StompCommand.CONNECT == accessor.command) {
                accessor.user = Principal {
                    checkNotNull(accessor.getNativeHeader(USER_ID_HEADER))
                    accessor.getNativeHeader(USER_ID_HEADER)?.get(0)
                }
            }
            return message
        } ?: throw AuthenticationException("There is no headers in stomp connection message")
    }
}