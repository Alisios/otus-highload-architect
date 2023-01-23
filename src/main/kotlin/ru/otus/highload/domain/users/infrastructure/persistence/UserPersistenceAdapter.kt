package ru.otus.highload.domain.users.infrastructure.persistence

import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional
import ru.otus.highload.domain.users.core.model.UserDto
import ru.otus.highload.domain.users.core.model.toDto
import ru.otus.highload.domain.users.core.model.toEntity
import ru.otus.highload.domain.users.core.ports.out.UserPersistencePort
import ru.otus.highload.domain.users.infrastructure.persistence.entity.UserRepository
import java.util.UUID

@Service
@Transactional
class UserPersistenceAdapter(private val userRepository: UserRepository) : UserPersistencePort {

    override fun save(user: UserDto) {
        userRepository.save(user.toEntity())
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    override fun getById(id: UUID): UserDto? = userRepository.findByIdOrNull(id)?.toDto()
}
