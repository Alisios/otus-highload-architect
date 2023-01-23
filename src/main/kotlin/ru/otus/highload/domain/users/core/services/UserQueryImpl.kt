package ru.otus.highload.domain.users.core.services

import org.springframework.stereotype.Service
import ru.otus.highload.domain.commmon.exceptions.NotFoundUserException
import ru.otus.highload.domain.users.core.model.UserDto
import ru.otus.highload.domain.users.core.ports.`in`.UserQuery
import ru.otus.highload.domain.users.core.ports.out.UserPersistencePort
import java.util.UUID

@Service
class UserQueryImpl(private val userPersistencePort: UserPersistencePort) : UserQuery {

    override fun registerUser(userDto: UserDto) {
        userPersistencePort.save(userDto)
    }

    override fun getById(id: UUID): UserDto {
        return userPersistencePort.getById(id)
            ?: throw NotFoundUserException("Пользователь с заданным идентификатором не найден")
    }
}