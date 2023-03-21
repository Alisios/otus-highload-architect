package ru.otus.highload.domain.users.core.ports.out

import ru.otus.highload.domain.users.core.model.PostDto

interface PostPersistencePort {

    fun addPost(postDto: PostDto)

    fun updatePost(postDto: PostDto)

    fun deleteByPostId(postId: String)

    fun deleteByUserId(userId: String)

    fun getByPostId(postId: String): PostDto?

    fun getByUserId(userId: String): List<PostDto>

    fun getFriendsFeed(userId: String, offset: Long, number: Long): List<PostDto>
}
