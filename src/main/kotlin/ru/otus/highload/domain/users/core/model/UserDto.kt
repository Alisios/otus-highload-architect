package ru.otus.highload.domain.users.core.model

//import io.swagger.v3.oas.annotations.media.Schema
import ru.otus.highload.domain.users.infrastructure.persistence.entity.UserEntity

//@Schema(name = "Дто пользователя")
data class UserDto(

//    @field:Schema(
//        description = "Идентификатор пользователя",
//        example = "cfc893bf-cad9-4fa5-941f-d4627ee783e3"
//    )
    val id: String,

//    @field:Schema(
//        description = "Имя",
//        example = "Анна"
//    )
    val name: String,

//    @field:Schema(
//        description = "Фамилия",
//        example = "Смирнова"
//    )
    val surname: String,

//    @field:Schema(
//        description = "Возраст",
//        example = "26"
//    )
    val age: Int,

//    @field:Schema(
//        description = "Пол",
//        example = "Ж"
//    )
    val gender: String,

//    @field:Schema(
//        description = "Интересы",
//        example = "Гитара"
//    )
    val interests: MutableSet<String>,

//    @field:Schema(
//        description = "Город",
//        example = "Санкт-Петербург"
//    )
    val city: String?,
)

fun UserEntity.toDto() = UserDto(
    id = id,
    name = name,
    surname = surname,
    age = age,
    interests = interests,
    city = city,
    gender = gender
)
