package ru.otus.highload.users.core.db.user

interface UserRepository {

    fun insert(userEntity: UserEntity)

    fun findByIdOrNull(id: String): UserEntity?

    fun deleteById(id: String)

    fun getByLogin(login: String): UserEntity?

    fun getByFirstNameAndLastName(firstName: String, secondName: String): List<UserEntity>
}
