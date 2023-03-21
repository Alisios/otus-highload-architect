package ru.otus.highload.domain.users.application.web

import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RestController
import ru.otus.highload.domain.users.core.model.FriendDto
import ru.otus.highload.domain.users.core.ports.`in`.FriendQuery

@Tag(name = "Добавление/удаление друга пользователя")
@RestController
@CrossOrigin
class FriendController(private val friendQuery: FriendQuery) {

    @PutMapping("/friend/set/{friendId}")
    fun addFriend(@PathVariable("friendId") friendId: String) =
        friendQuery.addFriend(friendId, SecurityContextHolder.getContext().authentication.name)

    @PutMapping("/friend/delete/{friendId}")
    fun deleteFriend(@PathVariable("friendId") friendId: String) =
        friendQuery.deleteFriend(friendId, SecurityContextHolder.getContext().authentication.name)

    @GetMapping("/friend/list")
    fun getAllFriends(): List<FriendDto> =
        friendQuery.getAllFriends(SecurityContextHolder.getContext().authentication.name)
}
