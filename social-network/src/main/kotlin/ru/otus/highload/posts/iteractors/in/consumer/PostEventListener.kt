package ru.otus.highload.posts.iteractors.`in`.consumer

import mu.KLogging
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component
import ru.otus.highload.posts.core.PostCacheService
import ru.otus.highload.posts.core.model.events.AddPostEvent
import ru.otus.highload.posts.core.model.events.DeletePostEvent
import ru.otus.highload.posts.core.model.events.UpdatePostEvent

@Component
class PostEventListener(
    private val postCacheService: PostCacheService
) {
    private companion object : KLogging()

    @EventListener(classes = [AddPostEvent::class])
    fun addPostEventListener(event: AddPostEvent) {
        logger.info { "Событие о добавлении поста: $event получено" }
        runCatching {
            event.friendIds.forEach { postCacheService.addPostByUserId(it, event.postDto) }
        }.onFailure {
            logger.error("Ошибка обработки события о добавлении поста: $event", it)
        }
    }

    @EventListener(classes = [DeletePostEvent::class])
    fun deletePostEventListener(event: DeletePostEvent) {
        logger.info { "Событие об удалении поста: $event получено" }
        runCatching {
            event.friendIds.forEach { postCacheService.updatePostFeedIfExists(it) }
        }.onFailure {
            logger.error("Ошибка обработки события об удалении поста: $event", it)
        }
    }

    @EventListener(classes = [UpdatePostEvent::class])
    fun updatePostEventListener(event: UpdatePostEvent) {
        logger.info { "Событие об обновлении поста: $event получено" }
        runCatching {
            event.friendIds.forEach { postCacheService.updatePostInCache(it, event.postDto) }
        }.onFailure {
            logger.error("Ошибка обработки события об обновлении поста: $event", it)
        }
    }
}
