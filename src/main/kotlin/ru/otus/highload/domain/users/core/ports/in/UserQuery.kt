package ru.otus.highload.domain.users.core.ports.`in`

import ru.otus.highload.domain.users.core.model.UserDto
import java.util.UUID

interface UserQuery {

    fun registerUser(userDto: UserDto)

    fun  getById(id: UUID) : UserDto
}