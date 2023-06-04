package ru.otus.highload.posts.iteractors.`in`.consumer

import mu.KLogging
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component
import ru.otus.highload.posts.core.PostCacheService
import ru.otus.highload.posts.core.model.events.AddPostEvent
import ru.otus.highload.users.core.model.events.DeleteUserEvent

@Component
class UserEventListener(
    private val postCacheService: PostCacheService
) {
    private companion object : KLogging()

    @EventListener(classes = [DeleteUserEvent::class])
    fun deleteUserEventListener(event: DeleteUserEvent) {
        logger.info { "Событие об удалении пользователя: $event" }
        runCatching {
            event.friendIds.forEach { postCacheService.updatePostFeedIfExists(it) }
            postCacheService.deleteCache(event.userId)
        }.onFailure {
            logger.error("Ошибка обработки события об удалении пользователя: $event", it)
        }
    }
}
