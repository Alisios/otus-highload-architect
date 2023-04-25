package ru.otus.highload.posts.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties(prefix = "app.rabbit.mq")
data class RabbitMqSettings(
        val login: String,
        val password: String,
        val systemLogin: String,
        val systemPassword: String,
        val host: String,
        val port: Int,
)
