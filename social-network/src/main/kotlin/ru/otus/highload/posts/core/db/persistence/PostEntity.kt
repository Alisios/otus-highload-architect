package ru.otus.highload.posts.core.db.persistence

import java.time.LocalDateTime

data class PostEntity(
    val postId: String,
    val userId: String,
    val postText: String,
    var createDate: LocalDateTime? = null,
    var modifyDate: LocalDateTime? = null
)
