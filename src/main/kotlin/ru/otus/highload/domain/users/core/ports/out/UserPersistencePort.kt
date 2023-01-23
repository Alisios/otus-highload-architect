package ru.otus.highload.domain.users.core.ports.out

import ru.otus.highload.domain.users.core.model.UserDto
import java.util.UUID

interface UserPersistencePort {

    fun save(user: UserDto)

    fun getById(id: UUID) : UserDto?
}
