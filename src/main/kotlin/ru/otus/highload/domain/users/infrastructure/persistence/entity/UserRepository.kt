package ru.otus.highload.domain.users.infrastructure.persistence.entity

import org.springframework.data.repository.CrudRepository
import java.util.UUID

interface UserRepository : CrudRepository<UserEntity, UUID>