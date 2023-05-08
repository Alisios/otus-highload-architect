package ru.otus.highload.posts.config.ws

import org.springframework.context.annotation.Configuration
import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.messaging.simp.config.ChannelRegistration
import org.springframework.messaging.simp.config.MessageBrokerRegistry
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler
import org.springframework.web.socket.config.annotation.EnableWebSocket
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker
import org.springframework.web.socket.config.annotation.StompEndpointRegistry
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer
import org.springframework.web.socket.server.HandshakeInterceptor
import ru.otus.highload.posts.config.RabbitMqSettings
import ru.otus.highload.posts.iteractors.`in`.websocket.AuthChannelWsInterceptor

@Configuration
@EnableWebSocketMessageBroker
@EnableWebSocket
@Order(Ordered.HIGHEST_PRECEDENCE + 99)
class WebsocketConfig(
        private val rabbitMqSettings: RabbitMqSettings,
        private val websocketSettings: WebsocketSettings,
        private val authChannelWsInterceptor: AuthChannelWsInterceptor,
) : WebSocketMessageBrokerConfigurer {
    override fun configureClientInboundChannel(registration: ChannelRegistration) {
        registration.interceptors(authChannelWsInterceptor)
    }

    override fun configureMessageBroker(registry: MessageBrokerRegistry) {
        //Префикс куда будут направлены сообщения на фронт. Пример /client/download-link,  /client/error
        val taskScheduler = ThreadPoolTaskScheduler()
        taskScheduler.initialize()
        registry.enableStompBrokerRelay(websocketSettings.topicPrefix, websocketSettings.queuePrefix)
                .setClientLogin(rabbitMqSettings.login)
                .setClientPasscode(rabbitMqSettings.password)
                .setSystemLogin(rabbitMqSettings.systemLogin)
                .setSystemPasscode(rabbitMqSettings.systemPassword)
                .setRelayHost(rabbitMqSettings.host)
                .setRelayPort(rabbitMqSettings.port)
                .setUserDestinationBroadcast("/topic/unresolved.user.dest")
                .setUserRegistryBroadcast("/topic/registry.broadcast")
                .setTaskScheduler(taskScheduler)
        registry.setApplicationDestinationPrefixes(websocketSettings.applicationDestinationPrefix);
    }

    override fun registerStompEndpoints(registry: StompEndpointRegistry) {
        //Точка подключения для HTTP для создания сокет соединения
        //при создании веб соединения в JS указываем полный путь(включающий server.servlet.context-path установленный в application.properties! http://localhost:8080/emulator-websocket-endpoint)
        registry.addEndpoint(websocketSettings.wsEndpoint)
                //Если клиент и сторона сервера используют разные домены, обеспечивает связь между ними.
                .setAllowedOriginPatterns("*")
                // Включает резервные параметры SockJS (Позволит WebSocket работать, даже если протокол WebSocket не поддерживается браузером)
                .withSockJS();
    }
}
