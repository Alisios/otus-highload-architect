package ru.otus.highload.posts.iteractors.out

import mu.KLogging
import org.springframework.context.ApplicationEventPublisher
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Service
import ru.otus.highload.posts.config.POST_EXECUTOR_NAME
import ru.otus.highload.posts.core.PostProducer
import ru.otus.highload.posts.core.model.PostDto
import ru.otus.highload.posts.core.model.events.AddPostEvent
import ru.otus.highload.posts.core.model.events.DeletePostEvent
import ru.otus.highload.posts.core.model.events.UpdatePostEvent
import ru.otus.highload.users.core.FriendsFacade

@Service
class EventContextPostProducer(
    private val applicationEventPublisher: ApplicationEventPublisher,
    private val friendsFacade: FriendsFacade
) : PostProducer {

    private companion object : KLogging()

    @Async(POST_EXECUTOR_NAME)
    override fun sendCreateEvent(userId: String, postDto: PostDto) {
        runCatching {
            applicationEventPublisher.publishEvent(
                AddPostEvent(
                    postDto = postDto,
                    friendIds = friendsFacade.getUsersByFriendId(userId)
                )
            )
        }
            .onFailure { logger.error { "Ошибка отправки события о создании поста $postDto для пользователя $userId" } }
            .onSuccess { logger.info { "Событие о создании поста $postDto для пользователя $userId успешно отправлено" } }
    }

    @Async(POST_EXECUTOR_NAME)
    override fun sendUpdateEvent(userId: String, postDto: PostDto) {
        runCatching {
            applicationEventPublisher.publishEvent(
                UpdatePostEvent(
                    postDto = postDto,
                    friendIds = friendsFacade.getUsersByFriendId(userId)
                )
            )
        }
            .onFailure { logger.error { "Ошибка отправки события об обновлении поста $postDto для пользователя $userId" } }
            .onSuccess { logger.info { "Событие об обновлении поста $postDto для пользователя $userId успешно отправлено" } }
    }

    @Async(POST_EXECUTOR_NAME)
    override fun sendDeleteEvent(userId: String, postId: String) {
        runCatching {
            applicationEventPublisher.publishEvent(
                DeletePostEvent(
                    postId = postId,
                    friendIds = friendsFacade.getUsersByFriendId(userId)
                )
            )
        }
            .onFailure { logger.error { "Ошибка отправки события об удалении поста $postId для пользователя $userId" } }
            .onSuccess { logger.info { "Событие об удалении поста $postId для пользователя $userId успешно отправлено" } }
    }
}
