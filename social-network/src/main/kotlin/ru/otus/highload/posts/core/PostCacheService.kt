package ru.otus.highload.posts.core

import ru.otus.highload.posts.core.model.PostDto

interface PostCacheService {

    fun updatePostInCache(userId: String, postDto: PostDto)

    fun updatePostFeedIfExists(userId: String)

    fun addPostByUserId(userId: String, postDto: PostDto)

    fun addFriendPostsById(userId: String, friendId: String)

    fun getCache(userId: String, offset: Long, number: Long): List<PostDto>

    fun deleteCache(userId: String)
}
