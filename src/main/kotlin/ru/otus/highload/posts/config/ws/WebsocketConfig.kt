package ru.otus.highload.posts.config.ws

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.messaging.simp.SimpMessageType
import org.springframework.messaging.simp.config.MessageBrokerRegistry
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler
import org.springframework.session.Session
import org.springframework.session.SessionRepository
import org.springframework.session.web.socket.server.SessionRepositoryMessageInterceptor
import org.springframework.web.socket.config.annotation.*
import ru.otus.highload.posts.config.RabbitMqSettings
import java.util.*


@Configuration
class WebsocketConfig(
        private val rabbitMqSettings: RabbitMqSettings,
        private val websocketSettings: WebsocketSettings,
      //  private val sessionRepository: SessionRepository<out Session?>
) : WebSocketMessageBrokerConfigurer {

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
                //.setUserDestinationBroadcast()
                .setTaskScheduler(taskScheduler)
        registry.setApplicationDestinationPrefixes(websocketSettings.applicationDestinationPrefix);
    }

//    override fun configureWebSocketTransport(registration: WebSocketTransportRegistration) {
//        registration.setSendTimeLimit(15 * 1000).setSendBufferSizeLimit(512 * 1024)
//    }

    //устанавливаем пользователя исходя из actorId с UI
//    override fun configureClientInboundChannel(registration: ChannelRegistration) {
//        registration.interceptors(object : ChannelInterceptor {
//
//            fun preSend(message: Message<*>?, channel: MessageChannel): Message<*>? {
//                val accessor = MessageHeaderAccessor.getAccessor(message!!, StompHeaderAccessor::class.java)
//                if (StompCommand.CONNECT == accessor!!.command) {
//                    accessor.user = Principal { // log.debug("ActorID: {}", accessor.getNativeHeader("actorID"));
//                        Objects.requireNonNull(accessor.getNativeHeader("actorID"), "actorID при подключении вебсокетов не может быть равно null")[0]
//                    }
//                }
//                accessor.setLeaveMutable(false)
//                //log.debug("Пользователь  подключился по вебсокетам {}", accessor.getUser());
//                return message
//            }
//        })
//    }

    override fun registerStompEndpoints(registry: StompEndpointRegistry) {
        //Точка подключения для HTTP для создания сокет соединения
        //при создании веб соединения в JS указываем полный путь(включающий server.servlet.context-path установленный в application.properties! http://localhost:8080/emulator-websocket-endpoint)
        registry.addEndpoint(websocketSettings.wsEndpoint)
                //Если клиент и сторона сервера используют разные домены, обеспечивает связь между ними.
                .setAllowedOrigins("*")
                // Включает резервные параметры SockJS (Позволит WebSocket работать, даже если протокол WebSocket не поддерживается браузером)
                .withSockJS();
    }

//    @Bean
//    fun sessionRepositoryInterceptor(): SessionRepositoryMessageInterceptor<out Session?>? {
//        val interceptor: SessionRepositoryMessageInterceptor<out Session?> = SessionRepositoryMessageInterceptor(sessionRepository)
//        interceptor.setMatchingMessageTypes(EnumSet.of(SimpMessageType.CONNECT, SimpMessageType.MESSAGE, SimpMessageType.SUBSCRIBE, SimpMessageType.UNSUBSCRIBE, SimpMessageType.HEARTBEAT))
//        return interceptor
//    }
}
