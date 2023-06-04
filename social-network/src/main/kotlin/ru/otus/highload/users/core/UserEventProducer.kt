package ru.otus.highload.users.core

interface UserEventProducer {

    fun sendDeleteUser(userId: String, friendIds: List<String>)

    fun sendDeleteFriend(userId: String, friendId: String)

    fun sendAddFriend(userId: String, friendId: String)
}
