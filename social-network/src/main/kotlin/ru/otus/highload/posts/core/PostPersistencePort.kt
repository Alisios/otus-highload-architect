package ru.otus.highload.posts.core

import ru.otus.highload.posts.core.model.PostDto

interface PostPersistencePort {

    fun addPost(postDto: PostDto)

    fun updatePost(postDto: PostDto)

    fun deleteByPostId(postId: String)

    fun deleteByUserId(userId: String)

    fun getByPostId(postId: String): PostDto?

    fun getByUserId(userId: String): MutableList<PostDto>

    fun getFriendsFeed(userId: String, offset: Long? = 0, number: Long? = 1000): MutableList<PostDto>
}
