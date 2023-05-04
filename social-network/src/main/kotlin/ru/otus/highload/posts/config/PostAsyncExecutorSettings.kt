package ru.otus.highload.posts.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties(prefix = "app.posts.executor")
data class PostAsyncExecutorSettings(

    val maxPoolSize: Int,

    val corePoolSize: Int,

    val keepAliveSeconds: Int,

    val awaitTerminationTimeSeconds: Int
)
