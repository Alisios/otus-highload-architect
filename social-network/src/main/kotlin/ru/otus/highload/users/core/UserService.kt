package ru.otus.highload.users.core

import org.springframework.validation.annotation.Validated
import ru.otus.highload.users.core.model.NewUserDto
import ru.otus.highload.users.core.model.UserDto
import java.util.UUID
import javax.validation.Valid
import javax.validation.constraints.NotEmpty

@Validated
interface UserService {

    fun registerUser(@Valid newUserDto: NewUserDto): String

    fun getById(@NotEmpty(message = "Идентификатор друга не может быть пустым") id: String): UserDto

    fun deleteById(@NotEmpty(message = "Идентификатор друга не может быть пустым") id: String)

    fun getByLogin(@NotEmpty(message = "Идентификатор друга не может быть пустым") login: String): UserDto

    fun getByFirstNameAndLastName(
        @NotEmpty(message = "Идентификатор друга не может быть пустым") firstName: String,
        @NotEmpty(message = "Идентификатор друга не может быть пустым") lastName: String
    ): List<UserDto>
}
