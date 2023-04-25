package ru.otus.highload.users.core.services

import org.springframework.stereotype.Service
import ru.otus.highload.users.core.FriendService
import ru.otus.highload.users.core.FriendsFacade

@Service
class FriendsFacadeImpl(
    private val friendService: FriendService
) : FriendsFacade {

    override fun getUsersByFriendId(friendId: String): List<String> =
        friendService.getAllUsersByFriendId(friendId).map { it.userId }
}
