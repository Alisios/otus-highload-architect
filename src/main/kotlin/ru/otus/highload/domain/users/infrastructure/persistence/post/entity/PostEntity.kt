package ru.otus.highload.domain.users.infrastructure.persistence.post.entity

import java.time.LocalDateTime

data class PostEntity(
    val postId: String,
    val userId: String,
    val postText: String,
    var createDate: LocalDateTime? = null,
    var modifyDate: LocalDateTime? = null
)
