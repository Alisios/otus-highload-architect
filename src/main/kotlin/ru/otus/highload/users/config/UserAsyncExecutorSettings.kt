package ru.otus.highload.users.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties(prefix = "app.users.executor")
data class UserAsyncExecutorSettings(

    val maxPoolSize: Int,

    val corePoolSize: Int,

    val keepAliveSeconds: Int,

    val awaitTerminationTimeSeconds: Int
)
