package ru.otus.highload.posts.core.model.events

import ru.otus.highload.common.EventType
import ru.otus.highload.posts.core.model.PostDto

data class UpdatePostEvent(
    val type: EventType = EventType.UPDATE_POST_EVENT,
    val postDto: PostDto,
    val friendIds: List<String>
)

