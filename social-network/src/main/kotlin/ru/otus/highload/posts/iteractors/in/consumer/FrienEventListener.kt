package ru.otus.highload.posts.iteractors.`in`.consumer

import mu.KLogging
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component
import ru.otus.highload.posts.core.PostCacheService
import ru.otus.highload.posts.core.model.events.AddPostEvent
import ru.otus.highload.users.core.model.events.AddFriendEvent
import ru.otus.highload.users.core.model.events.DeleteFriendEvent

@Component
class FriendEventListener(
    private val postCacheService: PostCacheService
) {

    private companion object : KLogging()

    @EventListener(classes = [AddFriendEvent::class])
    fun addFriendEventListener(event: AddFriendEvent) {
        logger.info { "Событие о добавлении друга: $event получено" }
        runCatching {
            postCacheService.addFriendPostsById(event.userId, event.friendId)
        }.onFailure {
            logger.error("Ошибка обработки события о добавлении друга: $event", it)
        }
    }


    @EventListener(classes = [DeleteFriendEvent::class])
    fun deleteFriendEventListener(event: DeleteFriendEvent) {
        logger.info { "Событие об удалении друга: $event получено" }
        runCatching {
            postCacheService.updatePostFeedIfExists(event.userId)
        }.onFailure {
            logger.error("Ошибка обработки события об удалении друга: $event", it)
        }
    }
}

