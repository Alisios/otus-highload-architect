package ru.otus.highload.posts.config.cache

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
import ru.otus.highload.posts.core.cache.post.PostFeedEntity
import ru.otus.highload.posts.core.cache.session.WsSessionEntity

@Configuration
class RedisCacheConfig(
        @Value("\${app.redis.posts.prefix}") private val redisPrefix: String
) {
    @Bean
    fun sessionRedisTemplate(
            connectionFactory: RedisConnectionFactory,
            objectMapper: ObjectMapper
    ) = RedisTemplate<String, PostFeedEntity>().apply {
        this.setConnectionFactory(connectionFactory)
        this.setValueSerializer(GenericJackson2JsonRedisSerializer(objectMapper))
    }

    // var repository: SessionRepository<out Session?> = RedisIndexedSessionRepository(redisTemplate)

    @Bean
    fun keyValueSessionMappingContext() =
            RedisMappingContext(MappingConfiguration(IndexConfiguration(), sessionRedisKeyspaceConfig()))

    @Bean
    fun sessionRedisKeyspaceConfig() = KeyspaceConfiguration().apply {
        this.addKeyspaceSettings(
                KeyspaceConfiguration.KeyspaceSettings(
                        WsSessionEntity::class.java,
                        "$redisPrefix:ws-session"
                )
        )
    }
}
