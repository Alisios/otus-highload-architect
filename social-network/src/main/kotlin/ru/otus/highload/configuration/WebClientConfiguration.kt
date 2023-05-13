package ru.otus.highload.configuration

import io.netty.channel.ChannelOption
import io.netty.handler.timeout.ReadTimeoutHandler
import io.netty.handler.timeout.WriteTimeoutHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.client.reactive.ReactorClientHttpConnector
import org.springframework.web.reactive.function.client.WebClient
import reactor.netty.http.client.HttpClient
import ru.otus.highload.chats.core.config.ChatSettings
import java.time.Duration
import java.util.concurrent.TimeUnit


@Configuration
class WebClientConfiguration(private val chatSettings: ChatSettings) {

    private fun httpClient(): HttpClient = HttpClient.create()
            .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
            .responseTimeout(Duration.ofMillis(5000))
            .doOnConnected { conn ->
                conn.addHandlerLast(ReadTimeoutHandler(5000, TimeUnit.MILLISECONDS))
                        .addHandlerLast(WriteTimeoutHandler(5000, TimeUnit.MILLISECONDS))
            }

    @Bean
    fun chatWebClient(): WebClient =
            WebClient.builder()
                    .baseUrl(chatSettings.baseUrl)
                    .clientConnector(ReactorClientHttpConnector(httpClient()))
                    .build()
}