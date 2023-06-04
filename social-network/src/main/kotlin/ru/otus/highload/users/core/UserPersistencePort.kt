package ru.otus.highload.users.core

import ru.otus.highload.users.core.model.NewUserDto
import ru.otus.highload.users.core.model.UserDto

interface UserPersistencePort {

    fun save(user: NewUserDto): String

    fun getById(id: String): UserDto?

    fun deleteById(id: String)

    fun getByLogin(login: String): UserDto?

    fun getByFirstNameAndLastName(firstName: String, secondName: String): List<UserDto>
}
