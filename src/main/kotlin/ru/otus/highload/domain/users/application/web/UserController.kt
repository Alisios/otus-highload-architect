package ru.otus.highload.domain.users.application.web

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import ru.otus.highload.domain.users.core.model.UserDto
import ru.otus.highload.domain.users.core.ports.`in`.UserQuery
import java.util.UUID

@RestController
class UserController(private val userQuery: UserQuery) {

    @PostMapping("user")
    fun register(@RequestBody userDto: UserDto) {
        userQuery.registerUser(userDto)
    }

    @GetMapping("user/{id}")
    fun getUserById(@PathVariable id: UUID): UserDto = userQuery.getById(id)
}
