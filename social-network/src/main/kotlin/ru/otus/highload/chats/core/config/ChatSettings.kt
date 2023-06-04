package ru.otus.highload.chats.core.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties(prefix = "app.chat")
data class ChatSettings(
        val baseUrl: String,
        val userChatSuffixUrl: String
)
