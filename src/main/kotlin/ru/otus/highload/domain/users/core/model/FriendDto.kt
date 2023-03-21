package ru.otus.highload.domain.users.core.model

import ru.otus.highload.domain.users.infrastructure.persistence.friend.entity.FriendEntity
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
