package ru.otus.highload.configuration

import org.springframework.context.annotation.Configuration
import org.springframework.web.socket.config.annotation.EnableWebSocket
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker

@EnableWebSocketMessageBroker
@EnableWebSocket
@Configuration
class WebSocketConfiguration
