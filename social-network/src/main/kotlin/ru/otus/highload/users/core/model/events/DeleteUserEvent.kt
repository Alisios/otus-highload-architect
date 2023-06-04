package ru.otus.highload.users.core.model.events

import ru.otus.highload.common.EventType

data class DeleteUserEvent(
    val type: EventType = EventType.DELETE_USER_EVENT,
    val userId: String,
    val friendIds: List<String>
)
