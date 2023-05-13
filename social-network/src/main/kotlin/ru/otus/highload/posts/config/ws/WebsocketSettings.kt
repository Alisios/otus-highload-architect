package ru.otus.highload.posts.config.ws

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

public const val USER_ID_HEADER: String = "userId"
public const val POST_SUBSCRIBTION_SUFFIX: String = "/post"

@ConstructorBinding
@ConfigurationProperties(prefix = "app.websocket")
data class WebsocketSettings(

        val topicPrefix: String,
        val queuePrefix: String,
        val wsEndpoint: String,
        val applicationDestinationPrefix: String = "/app",
)
