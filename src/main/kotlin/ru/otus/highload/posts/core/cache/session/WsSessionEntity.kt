package ru.otus.highload.posts.core.cache.session

import org.springframework.data.annotation.Id
import org.springframework.data.redis.core.RedisHash
import java.time.LocalDateTime

@RedisHash(timeToLive = 86400)
data class WsSessionEntity(
        @Id
        val id: String,
        val userId: String,
        val created: LocalDateTime
)
