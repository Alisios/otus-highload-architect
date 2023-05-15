package ru.otus.highload.posts.iteractors.out

import mu.KLogging
import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.messaging.simp.user.SimpUserRegistry
import org.springframework.stereotype.Service
import ru.otus.highload.posts.config.ws.POST_SUBSCRIBTION_SUFFIX
import ru.otus.highload.posts.config.ws.WebsocketSettings
import ru.otus.highload.posts.core.SubscriberNotifier
import ru.otus.highload.posts.core.model.PostDto

@Service
class SubscriberNotifierWebsocket(
        private val registry: SimpUserRegistry,
        private val template: SimpMessagingTemplate,
        private val websocketSettings: WebsocketSettings
) : SubscriberNotifier {

    companion object : KLogging()

    override fun sendNewPostToSubscribers(friendIds: List<String>, postDto: PostDto) {
        friendIds
                .mapNotNull { registry.getUser(it) }
                .filter { !it.sessions.isNullOrEmpty() }
                .forEach {
                    template.convertAndSendToUser(it.name, websocketSettings.queuePrefix + POST_SUBSCRIBTION_SUFFIX, postDto)
                    logger.info { "Post for user ${it.name} was send to ${it.sessions}" }
                }
    }
}
