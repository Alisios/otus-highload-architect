package ru.otus.highload.users.core

import org.springframework.validation.annotation.Validated
import ru.otus.highload.users.core.model.FriendDto
import javax.validation.constraints.NotEmpty

@Validated
interface FriendService {

    fun addFriend(
        @NotEmpty(message = "Идентификатор друга не может быть пустым") friendId: String,
        @NotEmpty userId: String
    )

    fun deleteFriend(
        @NotEmpty(message = "Идентификатор друга не может быть пустым") friendId: String,
        @NotEmpty userId: String
    )

    fun getAllFriends(@NotEmpty userId: String): List<FriendDto>

    fun getAllUsersByFriendId(@NotEmpty(message = "Идентификатор друга не может быть пустым") friendId: String): List<FriendDto>
}
