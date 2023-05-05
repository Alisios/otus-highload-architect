package ru.otus.highload.posts.config.ws

import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.messaging.MessageSecurityMetadataSourceRegistry
import org.springframework.security.config.annotation.web.socket.AbstractSecurityWebSocketMessageBrokerConfigurer

@Configuration
class WebsocketSecurityConfig(private val websocketSettings: WebsocketSettings): AbstractSecurityWebSocketMessageBrokerConfigurer() {

    override fun configureInbound(messages: MessageSecurityMetadataSourceRegistry) {
        messages
                .simpTypeMatchers().permitAll()
                .simpDestMatchers(websocketSettings.applicationDestinationPrefix + "/**").permitAll()
                .simpSubscribeDestMatchers(websocketSettings.topicPrefix + "/**", websocketSettings.queuePrefix + "/**").permitAll()
                .anyMessage().permitAll()
//                .simpTypeMatchers(CONNECT, SUBSCRIBE, DISCONNECT).permitAll()
//                .simpDestMatchers("/app/**").authenticated()
//                .simpSubscribeDestMatchers("/topic/**").authenticated()
//                .anyMessage().permitAll()
    }

    override fun sameOriginDisabled(): Boolean {
        return true
    }
}
