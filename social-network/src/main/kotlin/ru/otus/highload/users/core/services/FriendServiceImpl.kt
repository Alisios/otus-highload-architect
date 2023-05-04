package ru.otus.highload.users.core.services

import org.springframework.stereotype.Service
import ru.otus.highload.users.core.FriendPersistentPort
import ru.otus.highload.users.core.FriendService
import ru.otus.highload.users.core.UserEventProducer
import ru.otus.highload.users.core.model.FriendDto

@Service
class FriendServiceImpl(
    private val friendPersistentPort: FriendPersistentPort,
    private val userEventProducer: UserEventProducer,
) : FriendService {

    override fun addFriend(friendId: String, userId: String) {
        friendPersistentPort.addFriend(friendId, userId)
        userEventProducer.sendAddFriend(userId, friendId)
    }

    override fun deleteFriend(friendId: String, userId: String) {
        friendPersistentPort.deleteFriend(friendId, userId)
        userEventProducer.sendDeleteFriend(userId, friendId)
    }

    override fun getAllFriends(userId: String): List<FriendDto> =
        friendPersistentPort.getAllFriends(userId)

    override fun getAllUsersByFriendId(friendId: String): List<FriendDto> =
        friendPersistentPort.getAllUsersByFriendId(friendId)
}
