package ru.otus.highload.domain.users.core.ports.`in`

import org.springframework.validation.annotation.Validated
import ru.otus.highload.domain.users.core.model.NewUserDto
import ru.otus.highload.domain.users.core.model.UserDto
import java.util.UUID
import javax.validation.Valid

@Validated
interface UserQuery {

    fun registerUser(@Valid newUserDto: NewUserDto): String

    fun getById(id: UUID): UserDto

    fun deleteById(id: UUID)

    fun getByLogin(login: String): UserDto
}
