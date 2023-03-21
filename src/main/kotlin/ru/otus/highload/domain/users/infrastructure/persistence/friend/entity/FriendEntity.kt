package ru.otus.highload.domain.users.infrastructure.persistence.friend.entity

import java.time.LocalDateTime

data class FriendEntity(
    val userId: String,
    val friendId: String,
    var createDate: LocalDateTime? = null,
)
