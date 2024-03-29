package ru.otus.highload.users.iteractors.`in`.web


import io.swagger.v3.oas.annotations.Hidden
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import ru.otus.highload.common.DefaultErrorResponse
import ru.otus.highload.users.core.UserService
import ru.otus.highload.users.core.model.NewUserDto
import ru.otus.highload.users.core.model.UserDto
import java.util.*


@Tag(name = "Получение и создание анкеты пользователя")
@RestController
@RequestMapping("/users")
@CrossOrigin
class UserController(private val userService: UserService) {

    @Operation(summary = "Регистрция нового пользователя")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "201", description = "Успешная регистрация",
                content = [Content(schema = Schema(implementation = String::class))]
            ),
            ApiResponse(responseCode = "401", description = "Unauthorized", content = [Content()]),
            ApiResponse(
                responseCode = "400", description = "Некорректные данные",
                content = [Content(schema = Schema(implementation = DefaultErrorResponse::class))]
            ),
            ApiResponse(
                responseCode = "404", description = "Пользователь не найден",
                content = [Content(schema = Schema(implementation = DefaultErrorResponse::class))]
            ),
            ApiResponse(
                responseCode = "500", description = "Внутренняя ошибка",
                content = [Content(schema = Schema(implementation = DefaultErrorResponse::class))]
            )
        ]
    )
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/register")
    fun register(
        @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "User",
            required = true,
            content = [Content(schema = Schema(implementation = NewUserDto::class))]
        )
        @org.springframework.web.bind.annotation.RequestBody newUserDto: NewUserDto
    ): String =
        userService.registerUser(newUserDto)

    @Operation(summary = "Получение анкеты пользователя")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200", description = "Успешное получение анкеты пользователя",
                content = [Content(schema = Schema(implementation = UserDto::class))]
            ),
            ApiResponse(responseCode = "401", description = "Unauthorized", content = [Content()]),
            ApiResponse(
                responseCode = "400", description = "Некорректные данные",
                content = [Content(schema = Schema(implementation = DefaultErrorResponse::class))]
            ),
            ApiResponse(
                responseCode = "404", description = "Ползователь не найден",
                content = [Content(schema = Schema(implementation = DefaultErrorResponse::class))]
            ),
            ApiResponse(
                responseCode = "500", description = "Внутренняя ошибка",
                content = [Content(schema = Schema(implementation = DefaultErrorResponse::class))]
            )
        ]
    )
    @GetMapping("/{id}")
    fun getUserById(
        @Parameter(
            description = "Идентификатор пользователя",
            required = true,
            example = "cfc893bf-cad9-4fa5-941f-d4627ee783e3"
        )
        @PathVariable id: UUID
    ): UserDto = userService.getById(id.toString())

    @GetMapping("/login")
    @Hidden
    fun getUserByLogin(
        @RequestParam("login") login: String
    ): UserDto = userService.getByLogin(login)

    @Operation(summary = "Поиск анкет")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200", description = "Успешный поиск пользователя",
                content = [Content(schema = Schema(implementation = UserDto::class))]
            ),
            ApiResponse(responseCode = "401", description = "Unauthorized", content = [Content()]),
            ApiResponse(
                responseCode = "400", description = "Некорректные данные",
                content = [Content(schema = Schema(implementation = DefaultErrorResponse::class))]
            ),
            ApiResponse(
                responseCode = "500", description = "Внутренняя ошибка",
                content = [Content(schema = Schema(implementation = DefaultErrorResponse::class))]
            )
        ]
    )
    @GetMapping("/search")
    fun getUserNameAndSurname(
        @Parameter(
            description = "Имя пользователя или его часть",
            required = true,
            example = "Саша"
        )
        @RequestParam("first_name") firstName: String,
        @Parameter(
            description = "Фамилия пользователя или его часть",
            required = true,
            example = "Лобанов"
        )
        @RequestParam("last_name") lastName: String
    ): List<UserDto> = userService.getByFirstNameAndLastName(firstName, lastName)
}
