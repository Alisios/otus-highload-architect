package ru.otus.highload.domain.users.infrastructure.persistence.user

import ru.otus.highload.domain.users.infrastructure.persistence.user.entity.UserEntity

interface UserRepository {

    fun insert(userEntity: UserEntity)

    fun findByIdOrNull(id: String): UserEntity?

    fun deleteById(id: String)

    fun getByLogin(login: String): UserEntity?

    fun getByFirstNameAndLastName(firstName: String, secondName: String): List<UserEntity>
}
