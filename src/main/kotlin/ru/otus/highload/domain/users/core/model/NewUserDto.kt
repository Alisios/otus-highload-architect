package ru.otus.highload.domain.users.core.model

import io.swagger.v3.oas.annotations.media.Schema
import ru.otus.highload.domain.users.infrastructure.persistence.user.entity.UserEntity
import java.util.*
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

@Schema(name = "Новый пользователь")
data class NewUserDto(

    @field:Schema(description = "Логин пользователя", example = "star")
    @field:NotBlank(message = "Логин не может быть пустым")
    @field:Size(min = 3, max = 50, message = "Длина логина должна быть от 3 до 50 символов")
    val login: String,

    @field:Schema(description = "Пароль", example = "123")
    @field:NotBlank(message = "Пароль не может быть пустым")
    @field:Size(min = 3, max = 50, message = "Длина пароля должна быть от 3 до 50 символов")
    val password: String,

    @field:Schema(description = "Имя пользователя", example = "Анна")
    @field:NotBlank(message = "Поле имя не может быть пустым")
    val name: String,

    @field:Schema(description = "Фамилия пользователя", example = "Смирнова")
    @field:NotBlank(message = "Поле фамилия не может быть пустым")
    val surname: String,

    @field:Schema(description = "Возраст", example = "26")
    @field:NotNull(message = "Поле возраст не может быть null")
    val age: Int,

    @field:Schema(description = "Пол", example = "Ж")
    @field:NotBlank(message = "Поле пола не может быть пустым")
    val gender: String,

    @field:Schema(description = "Интересы", example = "Гитара")
    val interests: MutableSet<String>,

    @field:Schema(description = "Город", example = "Санкт-Петербург")
    val city: String?,
)

fun NewUserDto.toEntity() = UserEntity(
    id = UUID.randomUUID().toString(),
    login = login,
    password = password,
    name = name,
    surname = surname,
    age = age,
    gender = gender,
    interests = interests,
    city = city,
    null,
    null
)

