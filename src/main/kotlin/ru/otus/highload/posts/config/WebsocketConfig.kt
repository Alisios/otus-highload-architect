package ru.otus.highload.posts.config

import org.springframework.context.annotation.Configuration
import org.springframework.messaging.simp.SimpMessageTypeMessageCondition.SUBSCRIBE
import org.springframework.messaging.simp.config.MessageBrokerRegistry
import org.springframework.security.config.annotation.web.messaging.MessageSecurityMetadataSourceRegistry
import org.springframework.security.config.annotation.web.socket.AbstractSecurityWebSocketMessageBrokerConfigurer
import org.springframework.web.socket.config.annotation.EnableWebSocket
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker
import org.springframework.web.socket.config.annotation.StompEndpointRegistry
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer


@Configuration
@EnableWebSocketMessageBroker
@EnableWebSocket
class WebsocketConfig(private val rabbitMqSettings: RabbitMqSettings, private val websocketSettings: WebsocketSettings) : AbstractSecurityWebSocketMessageBrokerConfigurer(), WebSocketMessageBrokerConfigurer {

//    fun configureMessageBroker(config: MessageBrokerRegistry) {
//        config.enableSimpleBroker("/topic")
//        config.setApplicationDestinationPrefixes("/app")
//    }

    override fun configureMessageBroker(registry: MessageBrokerRegistry) {
        registry.enableStompBrokerRelay(websocketSettings.topicPrefix, websocketSettings.queuePrefix)
                .setClientLogin(rabbitMqSettings.login)
                .setClientPasscode(rabbitMqSettings.password)
                .setSystemLogin(rabbitMqSettings.systemLogin)
                .setSystemPasscode(rabbitMqSettings.systemPassword)
                .setRelayHost(rabbitMqSettings.host)
                .setRelayPort(rabbitMqSettings.port)
        registry.setApplicationDestinationPrefixes(websocketSettings.applicationDestinationPrefix);
    }

    override fun registerStompEndpoints(registry: StompEndpointRegistry) {
        registry.addEndpoint(websocketSettings.wsEndpoint)
                .setAllowedOrigins("*")
                .withSockJS()
    }

    override fun configureInbound(messages: MessageSecurityMetadataSourceRegistry) {
        messages
                .simpTypeMatchers(CONNECT, SUBSCRIBE, DISCONNECT).permitAll()
                .simpDestMatchers("/app/**").authenticated()
                .simpSubscribeDestMatchers("/topic/**").authenticated()
                .anyMessage().authenticated()
    }

    override fun sameOriginDisabled(): Boolean {
        return true
    }

}
