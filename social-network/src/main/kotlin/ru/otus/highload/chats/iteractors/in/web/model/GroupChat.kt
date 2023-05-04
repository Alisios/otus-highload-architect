package ru.otus.highload.chats.iteractors.`in`.web.model

data class GroupChat(
    val name: String,
    val dialogId: String,
    val adminId: String,
    val participants: List<String>
) : Chat
