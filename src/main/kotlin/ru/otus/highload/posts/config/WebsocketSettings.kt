package ru.otus.highload.posts.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties(prefix = "app.websocket")
data class WebsocketSettings(

        val topicPrefix: String,
        val queuePrefix: String,
        val wsEndpoint: String,
        val applicationDestinationPrefix: String
)
