package ru.otus.highload.posts.core

import ru.otus.highload.posts.core.model.PostDto

interface SubscriberNotifier {

    fun sendNewPostToSubscribers(friendIds: List<String>, postDto: PostDto)
}
