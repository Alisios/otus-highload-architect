package ru.otus.highload.domain.users.core.ports.`in`

import org.springframework.validation.annotation.Validated
import ru.otus.highload.domain.users.core.model.FriendDto

@Validated
interface FriendQuery {

    fun addFriend(friendId: String, userId: String)

    fun deleteFriend(friendId: String, userId: String)

    fun getAllFriends(userId: String): List<FriendDto>
}
