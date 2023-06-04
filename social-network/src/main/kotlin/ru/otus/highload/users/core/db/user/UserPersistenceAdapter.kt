package ru.otus.highload.users.core.db.user

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional
import ru.otus.highload.users.core.model.NewUserDto
import ru.otus.highload.users.core.model.UserDto
import ru.otus.highload.users.core.model.toDto
import ru.otus.highload.users.core.model.toEntity
import ru.otus.highload.users.core.UserPersistencePort

@Service
@Transactional
class UserPersistenceAdapter(
    private val userRepository: UserRepository
) : UserPersistencePort {

    override fun save(user: NewUserDto): String {
        val userEntity = user.toEntity()
        userRepository.insert(userEntity)
        return userEntity.id
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    override fun getById(id: String): UserDto? = userRepository.findByIdOrNull(id)?.toDto()

    override fun deleteById(id: String) = userRepository.deleteById(id)

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    override fun getByLogin(login: String) =
        userRepository.getByLogin(login)?.toDto()

    override fun getByFirstNameAndLastName(firstName: String, secondName: String): List<UserDto> =
        userRepository.getByFirstNameAndLastName(firstName, secondName).map { it.toDto() }

}
