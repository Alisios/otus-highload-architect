package ru.otus.highload.chats.iteractors.out

import mu.KLogging
import org.springframework.stereotype.Service
import ru.otus.highload.chats.core.MessageProducer
import ru.otus.highload.chats.core.model.MessageDto
import ru.otus.highload.chats.core.model.MessageEvent

@Service
class MockMessageProducer : MessageProducer {

    private companion object : KLogging()

    override fun send(messageEvent: MessageDto) {
        logger.info { "Тут будет отправка по вебсокету" }
    }
}
