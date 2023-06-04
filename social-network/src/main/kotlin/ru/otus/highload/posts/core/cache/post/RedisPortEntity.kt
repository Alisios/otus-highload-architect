package ru.otus.highload.posts.core.cache.post

import org.springframework.data.annotation.Id
import org.springframework.data.redis.core.RedisHash
import ru.otus.highload.posts.core.model.PostDto
import java.time.LocalDateTime

@RedisHash(timeToLive = 120)
data class PostFeedEntity(

    @Id
    val id: String,
    val friendPosts: MutableList<PostDto>,
    val created: LocalDateTime
)
