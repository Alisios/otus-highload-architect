package ru.otus.highload.users.core.security

import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service
import ru.otus.highload.common.exception.IllegalLoginException
import ru.otus.highload.common.exception.NotFoundLoginException
import ru.otus.highload.users.core.db.user.UserRepository

const val LOGIN_PATTERN = "^[A-Za-z0-9]*\$"

@Service
class UserAuthenticationService(
    private val userRepository: UserRepository
) : UserDetailsService {

    override fun loadUserByUsername(login: String?): UserDetails {
        login.validateLogin()
        return login?.let { l ->
            userRepository.getByLogin(l)?.let {
                //User.withUsername(it.login)
                User.withUsername(it.id)
                    .password(it.password)
                    .authorities("USER")
                    .accountExpired(false)
                    .credentialsExpired(false)
                    .build()
            }
        } ?: throw NotFoundLoginException("User is not found")
    }
}

private fun String?.validateLogin() = apply {
    this.validateIfEmpty()
    this.validateLength()
    this?.validateIllegalCharacters()
}

private fun String?.validateIfEmpty() = apply {
    if (this.isNullOrBlank())
        throw IllegalLoginException("Логин не может быть пустым")
}

private fun String?.validateLength() = apply {
    if (this?.length!! > 50)
        throw IllegalLoginException("Длина логина не может превышать 50 символов")
}

private fun String.validateIllegalCharacters() = apply {
    if (!this.matches(LOGIN_PATTERN.toRegex()))
        throw IllegalLoginException("Логин содержит недопустимые символы. Логин может содержать только буквы и цифры на английском языке")
}
