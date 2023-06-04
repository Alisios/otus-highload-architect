package ru.otus.highload.users.core.db.friend

import java.time.LocalDateTime

data class FriendEntity(
    val userId: String,
    val friendId: String,
    var createDate: LocalDateTime? = null,
)
