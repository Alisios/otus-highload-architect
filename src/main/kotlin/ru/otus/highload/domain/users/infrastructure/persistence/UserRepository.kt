package ru.otus.highload.domain.users.infrastructure.persistence

import ru.otus.highload.domain.users.infrastructure.persistence.entity.UserEntity

interface UserRepository {

    fun insert(userEntity: UserEntity)

    fun findByIdOrNull(id: String): UserEntity?

    fun deleteById(id: String)

    fun getByLogin(login: String): UserEntity?
}
