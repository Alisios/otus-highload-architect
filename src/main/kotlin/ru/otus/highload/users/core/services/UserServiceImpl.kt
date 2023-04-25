package ru.otus.highload.users.core.services

import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import ru.otus.highload.common.exception.ExistedUserException
import ru.otus.highload.common.exception.NotFoundUserException
import ru.otus.highload.users.core.FriendPersistentPort
import ru.otus.highload.users.core.UserEventProducer
import ru.otus.highload.users.core.UserPersistencePort
import ru.otus.highload.users.core.UserService
import ru.otus.highload.users.core.model.NewUserDto
import ru.otus.highload.users.core.model.UserDto


@Service
class UserServiceImpl(
    private val encoder: PasswordEncoder,
    private val userPersistencePort: UserPersistencePort,
    private val userEventProducer: UserEventProducer,
    private val friendPersistentPort: FriendPersistentPort
) : UserService {

    override fun registerUser(newUserDto: NewUserDto): String =
        userPersistencePort.getByLogin(newUserDto.login)?.let {
            throw ExistedUserException("Пользователь с таким именем существует")
        } ?: userPersistencePort.save(newUserDto.copy(password = encoder.encode(newUserDto.password)))


    override fun getById(id: String): UserDto {
        return userPersistencePort.getById(id)
            ?: throw NotFoundUserException("Пользователь с заданным идентификатором не найден")
    }

    override fun deleteById(id: String) {
        return userPersistencePort.getById(id)?.let {
            val friendsIds = friendPersistentPort.getAllUsersByFriendId(id).map { it.friendId }
            userPersistencePort.deleteById(id)
            userEventProducer.sendDeleteUser(id, friendsIds)
        } ?: throw NotFoundUserException("Пользователь с заданным идентификатором не найден")
    }

    override fun getByLogin(login: String): UserDto {
        return userPersistencePort.getByLogin(login)
            ?: throw NotFoundUserException("Пользователь с заданным логиным не найден")
    }

    override fun getByFirstNameAndLastName(firstName: String, lastName: String): List<UserDto> {
        return userPersistencePort.getByFirstNameAndLastName(firstName, lastName)
    }
}
