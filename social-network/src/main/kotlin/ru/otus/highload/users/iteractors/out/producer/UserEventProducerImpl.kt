package ru.otus.highload.users.iteractors.out.producer

import mu.KLogging
import org.springframework.context.ApplicationEventPublisher
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Service
import ru.otus.highload.users.config.USER_EXECUTOR_NAME
import ru.otus.highload.users.core.UserEventProducer
import ru.otus.highload.users.core.model.events.AddFriendEvent
import ru.otus.highload.users.core.model.events.DeleteFriendEvent
import ru.otus.highload.users.core.model.events.DeleteUserEvent

@Service
class UserEventProducerImpl(
    private val applicationEventPublisher: ApplicationEventPublisher
) : UserEventProducer {

    private companion object : KLogging()

    @Async(USER_EXECUTOR_NAME)
    override fun sendDeleteUser(userId: String, friendIds: List<String>) {
        runCatching {
            applicationEventPublisher.publishEvent(
                DeleteUserEvent(userId = userId, friendIds = friendIds)
            )
        }
            .onFailure { logger.error { "Ошибка отправки события об удалении пользователя $userId" } }
            .onSuccess { logger.info { "Событие об удалении пользователя $userId успешно отправлено" } }

    }

    @Async(USER_EXECUTOR_NAME)
    override fun sendDeleteFriend(userId: String, friendId: String) {
        runCatching {
            applicationEventPublisher.publishEvent(
                DeleteFriendEvent(userId = userId, friendId = friendId)
            )
        }
            .onFailure { logger.error { "Ошибка отправки события об удалении друга $friendId для пользователя $userId" } }
            .onSuccess { logger.info { "Событие об удалении друга $friendId для пользователя $userId успешно отправлено" } }

    }

    @Async(USER_EXECUTOR_NAME)
    override fun sendAddFriend(userId: String, friendId: String) {
        runCatching {
            applicationEventPublisher.publishEvent(
                AddFriendEvent(userId = userId, friendId = friendId)
            )
        }
            .onFailure { logger.error { "Ошибка отправки события о создании друга $friendId для пользователя $userId" } }
            .onSuccess { logger.info { "Событие о создании друга $friendId для пользователя $userId успешно отправлено" } }
    }
}
