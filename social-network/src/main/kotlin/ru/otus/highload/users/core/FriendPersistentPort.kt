package ru.otus.highload.users.core

import org.springframework.validation.annotation.Validated
import ru.otus.highload.users.core.db.friend.FriendEntity
import ru.otus.highload.users.core.model.FriendDto

@Validated
interface FriendPersistentPort {

    fun addFriend(friendId: String, userId: String)

    fun deleteFriend(friendId: String, userId: String)

    fun getAllFriends(userId: String): List<FriendDto>

    fun getAllUsersByFriendId(friendId: String): List<FriendDto>
}
