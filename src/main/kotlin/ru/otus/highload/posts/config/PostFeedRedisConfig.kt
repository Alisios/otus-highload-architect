package ru.otus.highload.posts.config

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.core.convert.KeyspaceConfiguration
import org.springframework.data.redis.core.convert.MappingConfiguration
import org.springframework.data.redis.core.index.IndexConfiguration
import org.springframework.data.redis.core.mapping.RedisMappingContext
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer
import ru.otus.highload.posts.core.cache.PostFeedEntity

@Configuration
class PostFeedRedisConfig(
    @Value("\${app.redis.posts.prefix}") private val redisPrefix: String
) {
    @Bean
    fun postFeedRedisTemplate(
        connectionFactory: RedisConnectionFactory,
        objectMapper: ObjectMapper
    ) = RedisTemplate<String, PostFeedEntity>().apply {
        this.setConnectionFactory(connectionFactory)
        this.setValueSerializer(GenericJackson2JsonRedisSerializer(objectMapper))
    }

    @Bean
    fun keyValueMappingContext() =
        RedisMappingContext(MappingConfiguration(IndexConfiguration(), postsRedisKeyspaceConfig()))

    @Bean
    fun postsRedisKeyspaceConfig() = KeyspaceConfiguration().apply {
        this.addKeyspaceSettings(
            KeyspaceConfiguration.KeyspaceSettings(
                PostFeedEntity::class.java,
                "$redisPrefix:Posts"
            )
        )
    }
}

