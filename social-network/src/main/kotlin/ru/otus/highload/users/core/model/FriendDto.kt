package ru.otus.highload.users.core.model

import ru.otus.highload.users.core.db.friend.FriendEntity
import java.time.LocalDateTime

data class FriendDto(
    val userId: String,
    val friendId: String,
    var createDate: LocalDateTime? = null,
)

fun FriendEntity.toDto() = FriendDto(
    userId = userId,
    friendId = friendId,
    createDate = createDate
)
