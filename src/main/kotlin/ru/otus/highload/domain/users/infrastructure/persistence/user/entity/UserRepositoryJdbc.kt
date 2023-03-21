package ru.otus.highload.domain.users.infrastructure.persistence.user.entity

import org.springframework.jdbc.core.RowMapper
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.stereotype.Service
import ru.otus.highload.domain.users.infrastructure.persistence.user.UserRepository
import java.sql.ResultSet
import java.time.LocalDateTime
import java.util.*
import java.util.Map
import kotlin.collections.List
import kotlin.collections.isNotEmpty
import kotlin.collections.mapOf
import kotlin.collections.toMutableSet
import kotlin.collections.toTypedArray


@Suppress("UNCHECKED_CAST")
@Service
class UserRepositoryJdbc(
    private val namedParameterJdbcOperations: NamedParameterJdbcOperations,
    private val namedParameterJdbcTemplate: NamedParameterJdbcTemplate
) : UserRepository {

    private companion object {
        const val ID = "id"
        const val NAME = "name"
        const val SURNAME = "surname"
        const val AGE = "age"
        const val CITY = "city"
        const val GENDER = "gender"
        const val CREATED = "create_date"
        const val MODIFIED = "modify_date"
        const val INTEREST = "interests"
        const val LOGIN = "login"
        const val PASSWORD = "password"
    }

    override fun insert(userEntity: UserEntity) {
        val now = LocalDateTime.now()
        namedParameterJdbcTemplate.jdbcTemplate.update(
            "insert into social_network.users (id, login, password, name, surname, age, gender, city, create_date, modify_date, interests) values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
            userEntity.id,
            userEntity.login,
            userEntity.password,
            userEntity.name,
            userEntity.surname,
            userEntity.age,
            userEntity.gender,
            userEntity.city,
            now,
            now,
            userEntity.interests.toTypedArray()
        )
    }

    override fun findByIdOrNull(id: String): UserEntity? {
        val users: List<UserEntity> =
            namedParameterJdbcOperations.query(
                "select * from social_network.users where id = :id",
                mapOf(ID to id),
                userRowMapper
            )
        return users.takeIf { it.isNotEmpty() }?.get(0)
    }

    override fun deleteById(id: String) {
        namedParameterJdbcOperations.update("delete from social_network.users where id = :id", mapOf(ID to id))
    }

    override fun getByLogin(login: String): UserEntity? {
        val user: List<UserEntity> =
            namedParameterJdbcOperations.query(
                "select * from social_network.users where login = :login",
                mapOf(LOGIN to login),
                userRowMapper
            )

        return user.takeIf { it.isNotEmpty() }?.get(0)
    }

    override fun getByFirstNameAndLastName(firstName: String, secondName: String): List<UserEntity> {
        val users: List<UserEntity> = namedParameterJdbcOperations.query(
            // language=sql
            """
                    select * from social_network.users where name LIKE :name and surname LIKE :surname ORDER BY id
                   """,
            Map.of(
                NAME,
                "$firstName%",
                SURNAME,
                "$secondName%",
            ),
            userRowMapper
        )
        return users

    }

    private val userRowMapper = RowMapper { resultSet: ResultSet, i: Int ->
        UserEntity(
            id = resultSet.getString(ID),
            login = resultSet.getString(LOGIN),
            password = resultSet.getString(PASSWORD),
            name = resultSet.getString(NAME),
            surname = resultSet.getString(SURNAME),
            age = resultSet.getInt(AGE),
            gender = resultSet.getString(GENDER),
            city = resultSet.getString(CITY),
            interests = (resultSet.getArray(INTEREST).array as Array<String>).toMutableSet(),
            createDate = resultSet.getTimestamp(CREATED).toLocalDateTime(),
            modifyDate = resultSet.getTimestamp(MODIFIED).toLocalDateTime(),
        )
    }
}

