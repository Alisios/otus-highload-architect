package ru.otus.highload.posts.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties(prefix = "app.cache.redis")
data class CacheSettings(

    val postsMaxNumberPerUser: Long = 1000
)
