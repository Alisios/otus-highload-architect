package ru.otus.highload.domain.users.infrastructure.persistence.friend

import org.springframework.stereotype.Service
import ru.otus.highload.domain.users.core.model.FriendDto
import ru.otus.highload.domain.users.core.model.toDto
import ru.otus.highload.domain.users.core.ports.`in`.FriendQuery
import ru.otus.highload.domain.users.infrastructure.persistence.friend.entity.FriendEntity

@Service
class FriendPersistentAdapter(private val friendRepository: FriendRepository) : FriendQuery {

    override fun addFriend(friendId: String, userId: String) {
        friendRepository.addFriend(FriendEntity(userId, friendId))
    }
    override fun deleteFriend(friendId: String, userId: String) {
        friendRepository.deleteFriend(userId, friendId)
    }

    override fun getAllFriends(userId: String): List<FriendDto> =
        friendRepository.getAllFriends(userId).map { it.toDto() }

}
