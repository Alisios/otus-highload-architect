package ru.otus.highload.posts.core.model.events

import ru.otus.highload.common.EventType

data class DeletePostEvent(
    val type: EventType = EventType.DELETE_POST_EVENT,
    val postId: String,
    val friendIds: List<String>
)
