package ru.otus.highload.common.exception

class ChatServiceException(message: String?, th: Throwable) : RuntimeException(message, th)