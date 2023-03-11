package ru.otus.highload.domain.users.core.ports.out

import ru.otus.highload.domain.users.core.model.NewUserDto
import ru.otus.highload.domain.users.core.model.UserDto

interface UserPersistencePort {

    fun save(user: NewUserDto): String

    fun getById(id: String): UserDto?

    fun deleteById(id: String)

    fun getByLogin(login: String): UserDto?
}
