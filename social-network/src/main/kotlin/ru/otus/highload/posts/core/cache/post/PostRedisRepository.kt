package ru.otus.highload.posts.core.cache.post

import org.springframework.data.repository.CrudRepository
import ru.otus.highload.posts.core.cache.post.PostFeedEntity

interface PostRedisRepository : CrudRepository<PostFeedEntity, String>
