package ru.otus.highload.posts.core.cache.session

import org.springframework.data.repository.CrudRepository

interface SessionRedisRepository : CrudRepository<WsSessionEntity, String>
