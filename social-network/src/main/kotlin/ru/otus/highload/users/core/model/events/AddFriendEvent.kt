package ru.otus.highload.users.core.model.events

import ru.otus.highload.common.EventType

data class AddFriendEvent(
    val type: EventType = EventType.ADD_FRIEND_EVENT,
    val userId: String,
    val friendId: String
)
