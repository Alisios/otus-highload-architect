package ru.otus.highload.domain.users.application.web

//
//import io.swagger.v3.oas.annotations.Operation
//import io.swagger.v3.oas.annotations.Parameter
//import io.swagger.v3.oas.annotations.media.Content
//import io.swagger.v3.oas.annotations.media.Schema
//
//import io.swagger.v3.oas.annotations.responses.ApiResponse
//import io.swagger.v3.oas.annotations.responses.ApiResponses
//import io.swagger.v3.oas.annotations.tags.Tag
//import org.springframework.http.HttpStatus
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping

import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import ru.otus.highload.application.model.DefaultErrorResponse
import ru.otus.highload.domain.users.core.model.NewUserDto
import ru.otus.highload.domain.users.core.model.UserDto
import ru.otus.highload.domain.users.core.ports.`in`.UserQuery
import javax.validation.Valid

//@Tag(name = "Получение и создание анкеты пользователя")
@RestController
@Validated
@CrossOrigin
class UserController(private val userQuery: UserQuery) {

//    @Operation(summary = "Регистрция нового пользователя")
//    @ApiResponses(
//        value = [
//            ApiResponse(
//                responseCode = "201", description = "Успешная регистрация",
//                content = [Content(schema = Schema(implementation = String::class))]
//            ),
//            ApiResponse(responseCode = "401", description = "Unauthorized", content = [Content()]),
//            ApiResponse(
//                responseCode = "400",
//                description = "Некорректные данные",
//                content = [Content(schema = Schema(implementation = DefaultErrorResponse::class))]
//            ),
//            ApiResponse(
//                responseCode = "404",
//                description = "Пользователь не найден",
//                content = [Content(schema = Schema(implementation = DefaultErrorResponse::class))]
//            ),
//            ApiResponse(
//                responseCode = "500",
//                description = "Внутренняя ошибка",
//                content = [Content(schema = Schema(implementation = DefaultErrorResponse::class))]
//            )
//        ]
//    )
//    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/user/register")//, produces = ["application/json"], consumes = ["application/json"])
    fun register(
        @Valid
//        @io.swagger.v3.oas.annotations.parameters.RequestBody(
//            description = "Гыук",
//            required = true,
//            content = [Content(schema = Schema(implementation = NewUserDto::class))]
//        )
        @org.springframework.web.bind.annotation.RequestBody newUserDto: NewUserDto
    ): String =
        userQuery.registerUser(newUserDto)

//    @Operation(summary = "Получение анкеты пользователя")
//    @ApiResponses(
//        value = [
//            ApiResponse(
//                responseCode = "200",
//                description = "Успешное получение анкеты пользователя",
//                content = [Content(schema = Schema(implementation = UserDto::class))]
//            ),
//            ApiResponse(responseCode = "401", description = "Unauthorized", content = [Content()]),
//            ApiResponse(
//                responseCode = "400",
//                description = "Некорректные данные",
//                content = [Content(schema = Schema(implementation = DefaultErrorResponse::class))]
//            ),
//            ApiResponse(
//                responseCode = "404",
//                description = "Ползователь не найден",
//                content = [Content(schema = Schema(implementation = DefaultErrorResponse::class))]
//            ),
//            ApiResponse(
//                responseCode = "500",
//                description = "Внутренняя ошибка",
//                content = [Content(schema = Schema(implementation = DefaultErrorResponse::class))]
//            )
//        ]
//    )
    @GetMapping("/user/get/{id}")
    fun getUserById(
//        @Parameter(
//            description = "Идентификатор пользователя",
//            required = true,
//            example = "cfc893bf-cad9-4fa5-941f-d4627ee783e3"
//        )
        @PathVariable id: String
    ): UserDto = userQuery.getById(id)
}
