package ru.otus.highload.domain.users.infrastructure.persistence.friend

import ru.otus.highload.domain.users.infrastructure.persistence.friend.entity.FriendEntity

interface FriendRepository {

    fun addFriend(friendEntity: FriendEntity)

    fun getAllFriends(userId: String): List<FriendEntity>

    fun deleteFriend(userId: String, friendId: String)
}
