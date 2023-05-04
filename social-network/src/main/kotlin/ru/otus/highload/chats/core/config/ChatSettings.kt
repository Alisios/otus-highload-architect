package ru.otus.highload.chats.core.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties(prefix = "app.chat")
data class ChatSettings(
        val getChatUrl: String,
        val sendMessageUrl: String,
        val getChatByUserIdUrl: String,
        val getChatByChatId: String
)
