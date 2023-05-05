package ru.otus.highload.posts.iteractors.out

import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.messaging.simp.annotation.SubscribeMapping
import org.springframework.messaging.simp.user.SimpUserRegistry
import org.springframework.stereotype.Service
import ru.otus.highload.posts.config.ws.WebsocketSettings
import ru.otus.highload.posts.core.SubscriberNotifier
import ru.otus.highload.posts.core.model.PostDto

@Service
class SubscriberNotifierWebsocket(
        private val registry: SimpUserRegistry,
        private val template: SimpMessagingTemplate,
        private val websocketSettings: WebsocketSettings
) : SubscriberNotifier {

    @SubscribeMapping
    override fun sendNewPostToSubscribers(friendIds: List<String>, postDto: PostDto) {
        friendIds
                .filter { registry.getUser(it) != null }
                .filter { registry.getUser(it)!!.hasSessions() }
                .forEach { template.convertAndSendToUser(it, websocketSettings.topicPrefix, postDto) }
    }
}
