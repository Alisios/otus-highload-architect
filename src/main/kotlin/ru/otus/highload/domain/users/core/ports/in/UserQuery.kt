package ru.otus.highload.domain.users.core.ports.`in`

import ru.otus.highload.domain.users.core.model.NewUserDto
import ru.otus.highload.domain.users.core.model.UserDto

interface UserQuery {

    fun registerUser(newUserDto: NewUserDto): String

    fun getById(id: String): UserDto

    fun deleteById(id: String)
}
