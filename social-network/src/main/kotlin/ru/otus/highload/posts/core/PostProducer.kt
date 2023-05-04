package ru.otus.highload.posts.core

import ru.otus.highload.posts.core.model.PostDto

interface PostProducer {

    fun sendCreateEvent(userId: String, postDto: PostDto)

    fun sendUpdateEvent(userId: String, postDto: PostDto)

    fun sendDeleteEvent(userId: String, postId: String)
}
