package ru.otus.highload.common

//todo уйдет когда появится rabbitMq
enum class EventType {
    DELETE_POST_EVENT,
    ADD_POST_EVENT,
    UPDATE_POST_EVENT,
    DELETE_FRIEND_EVENT,
    DELETE_USER_EVENT,
    ADD_FRIEND_EVENT
}
