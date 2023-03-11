package ru.otus.highload.domain.users.infrastructure.persistence.entity

import java.time.LocalDateTime

data class UserEntity(
    val id: String,
    val login: String,
    val password: String,
    val name: String,
    val surname: String,
    val age: Int,
    val gender: String,
    val interests: MutableSet<String> = mutableSetOf(),

    val city: String?,
    var createDate: LocalDateTime? = null,

    var modifyDate: LocalDateTime? = null
)
