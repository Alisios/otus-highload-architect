package ru.otus.highload.users.core.model.events

import ru.otus.highload.common.EventType

data class DeleteFriendEvent(
    val type: EventType = EventType.DELETE_FRIEND_EVENT,
    val userId: String,
    val friendId: String
)
