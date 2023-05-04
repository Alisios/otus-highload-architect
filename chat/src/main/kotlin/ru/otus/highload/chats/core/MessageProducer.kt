package ru.otus.highload.chats.core

import ru.otus.highload.chats.core.model.MessageDto

interface MessageProducer {

    fun send(messageEvent: MessageDto)
}
