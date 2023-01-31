package ru.otus.highload.domain.users.core.services

import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import ru.otus.highload.domain.commmon.exceptions.ExistedUserException
import ru.otus.highload.domain.commmon.exceptions.NotFoundUserException
import ru.otus.highload.domain.users.core.model.NewUserDto
import ru.otus.highload.domain.users.core.model.UserDto
import ru.otus.highload.domain.users.core.ports.`in`.UserQuery
import ru.otus.highload.domain.users.core.ports.out.UserPersistencePort
import java.util.UUID

@Service
class UserQueryImpl(
    private val encoder: PasswordEncoder,
    private val userPersistencePort: UserPersistencePort
) : UserQuery {

    override fun registerUser(newUserDto: NewUserDto): String =
        userPersistencePort.getByLogin(newUserDto.login)?.let {
            throw ExistedUserException("Пользователь с таким именем существует")
        } ?: userPersistencePort.save(newUserDto.copy(password = encoder.encode(newUserDto.password)))


    override fun getById(id: UUID): UserDto {
        return userPersistencePort.getById(id.toString())
            ?: throw NotFoundUserException("Пользователь с заданным идентификатором не найден")
    }

    override fun deleteById(id: UUID) {
        return userPersistencePort.getById(id.toString())?.let { userPersistencePort.deleteById(id.toString()) }
            ?: throw NotFoundUserException("Пользователь с заданным идентификатором не найден")
    }
}
