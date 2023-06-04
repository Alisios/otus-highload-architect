package ru.otus.highload.users.core.db.friend

interface FriendRepository {

    fun addFriend(friendEntity: FriendEntity)

    fun getAllFriends(userId: String): List<FriendEntity>

    fun deleteFriend(userId: String, friendId: String)

    fun getAllUsersByFriendId(friendId: String): List<FriendEntity>
}
