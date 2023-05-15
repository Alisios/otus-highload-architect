package ru.otus.highload.posts.core

import ru.otus.highload.posts.core.model.PostDto

interface PostCacheProducer {

    fun sendCreateEvent(userId: String, friendIds: List<String>, postDto: PostDto)

    fun sendUpdateEvent(userId: String, friendIds: List<String>, postDto: PostDto)

    fun sendDeleteEvent(userId: String, friendIds: List<String>, postId: String)
}
