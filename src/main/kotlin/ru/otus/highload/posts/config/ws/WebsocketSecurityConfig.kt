package ru.otus.highload.posts.config.ws

import org.springframework.context.annotation.Configuration
import org.springframework.messaging.simp.SimpMessageType
import org.springframework.security.config.annotation.web.messaging.MessageSecurityMetadataSourceRegistry
import org.springframework.security.config.annotation.web.socket.AbstractSecurityWebSocketMessageBrokerConfigurer


@Configuration
class WebsocketSecurityConfig(private val websocketSettings: WebsocketSettings,): AbstractSecurityWebSocketMessageBrokerConfigurer() {

    override fun configureInbound(messages: MessageSecurityMetadataSourceRegistry) {
        messages
                .simpTypeMatchers(SimpMessageType.CONNECT, SimpMessageType.SUBSCRIBE, SimpMessageType.DISCONNECT).permitAll()
                .simpDestMatchers("${websocketSettings.applicationDestinationPrefix}/**").permitAll()
                .simpSubscribeDestMatchers("${websocketSettings.topicPrefix}/**","${websocketSettings.queuePrefix}/**").permitAll()
                .anyMessage().permitAll()
    }

    override fun sameOriginDisabled(): Boolean {
        return true
    }
}
