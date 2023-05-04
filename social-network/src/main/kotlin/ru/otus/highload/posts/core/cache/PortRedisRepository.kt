package ru.otus.highload.posts.core.cache

import org.springframework.data.repository.CrudRepository

interface PortRedisRepository : CrudRepository<PostFeedEntity, String>
