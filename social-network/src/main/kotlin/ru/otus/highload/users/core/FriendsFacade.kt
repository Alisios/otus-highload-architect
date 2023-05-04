package ru.otus.highload.users.core

interface FriendsFacade {

    fun getUsersByFriendId(friendId: String): List<String>
}
