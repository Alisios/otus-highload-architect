package ru.otus.highload.domain.users.core.model

import ru.otus.highload.domain.users.infrastructure.persistence.entity.UserEntity
import java.util.UUID

data class UserDto(
    val id: UUID,
    val name: String,
    val surname: String,
    val age: Int,
    val interests: Set<String>,
    val city: String?,
)

fun UserDto.toEntity() = UserEntity(
    id = id,
    name = name,
    surname = surname,
    age = age,
    interests = interests,
    city = city,
    null,
    null
)

fun UserEntity.toDto() = UserDto(
    id = id,
    name = name,
    surname = surname,
    age = age,
    interests = interests,
    city = city
)