package ru.otus.highload.posts.config.ws

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties(prefix = "app.websocket")
data class WebsocketSettings(

        val topicPrefix: String = "/topic",
        val queuePrefix: String = "/queue",
        val wsEndpoint: String = "/notification-websocket-endpoint",
        val applicationDestinationPrefix: String = "/app"
)
