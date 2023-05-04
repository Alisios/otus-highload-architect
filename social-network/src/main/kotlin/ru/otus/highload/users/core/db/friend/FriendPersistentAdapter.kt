package ru.otus.highload.users.core.db.friend

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional
import ru.otus.highload.users.core.model.FriendDto
import ru.otus.highload.users.core.model.toDto
import ru.otus.highload.users.core.FriendPersistentPort

@Service
@Transactional
class FriendPersistentAdapter(
    private val friendRepository: FriendRepository,
) : FriendPersistentPort {

    override fun addFriend(friendId: String, userId: String) {
        friendRepository.addFriend(FriendEntity(userId, friendId))
        //   friendRepository.addFriend(FriendEntity(friendId, userId))
    }

    override fun deleteFriend(friendId: String, userId: String) {
        friendRepository.deleteFriend(userId, friendId)
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    override fun getAllFriends(userId: String): List<FriendDto> =
        friendRepository.getAllFriends(userId).map { it.toDto() }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    override fun getAllUsersByFriendId(friendId: String): List<FriendDto> =
        friendRepository.getAllUsersByFriendId(friendId).map { it.toDto() }

}
