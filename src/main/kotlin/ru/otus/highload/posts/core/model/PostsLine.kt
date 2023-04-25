package ru.otus.highload.posts.core.model

data class PostsLine(
    val userId: String,
    val friendsPosts: List<PostDto>
)
