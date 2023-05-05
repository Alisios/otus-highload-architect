package ru.otus.highload.posts.iteractors.out

import mu.KLogging
import org.springframework.context.ApplicationEventPublisher
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Service
import ru.otus.highload.posts.config.async.POST_EXECUTOR_NAME
import ru.otus.highload.posts.core.PostCacheProducer
import ru.otus.highload.posts.core.model.PostDto
import ru.otus.highload.posts.core.model.events.AddPostEvent
import ru.otus.highload.posts.core.model.events.DeletePostEvent
import ru.otus.highload.posts.core.model.events.UpdatePostEvent

@Service
class EventContextPostCacheProducer(private val applicationEventPublisher: ApplicationEventPublisher) : PostCacheProducer {

    private companion object : KLogging()

    @Async(POST_EXECUTOR_NAME)
    override fun sendCreateEvent(userId: String, friendIds: List<String>, postDto: PostDto) {
        runCatching {
            applicationEventPublisher.publishEvent(
                    AddPostEvent(postDto = postDto, friendIds = friendIds)
            )
        }
                .onFailure { logger.error { "Ошибка отправки события о создании поста $postDto для пользователя $userId" } }
                .onSuccess { logger.info { "Событие о создании поста $postDto для пользователя $userId успешно отправлено" } }
    }

    @Async(POST_EXECUTOR_NAME)
    override fun sendUpdateEvent(userId: String, friendIds: List<String>, postDto: PostDto) {
        runCatching {
            applicationEventPublisher.publishEvent(
                    UpdatePostEvent(postDto = postDto, friendIds = friendIds)
            )
        }
                .onFailure { logger.error { "Ошибка отправки события об обновлении поста $postDto для пользователя $userId" } }
                .onSuccess { logger.info { "Событие об обновлении поста $postDto для пользователя $userId успешно отправлено" } }
    }

    @Async(POST_EXECUTOR_NAME)
    override fun sendDeleteEvent(userId: String, friendIds: List<String>, postId: String) {
        runCatching {
            applicationEventPublisher.publishEvent(
                    DeletePostEvent(postId = postId, friendIds = friendIds)
            )
        }
                .onFailure { logger.error { "Ошибка отправки события об удалении поста $postId для пользователя $userId" } }
                .onSuccess { logger.info { "Событие об удалении поста $postId для пользователя $userId успешно отправлено" } }
    }
}
