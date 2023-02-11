package ru.otus.highload.domain.users.infrastructure.persistence.entity

import org.springframework.jdbc.core.RowMapper
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.jdbc.core.namedparam.SqlParameterSource
import org.springframework.stereotype.Service
import ru.otus.highload.domain.users.infrastructure.persistence.UserRepository
import java.sql.ResultSet
import java.sql.Types
import java.time.LocalDateTime

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
        const val INTEREST = "interest"
        const val LOGIN = "login"
        const val PASSWORD = "password"
    }

    override fun insert(userEntity: UserEntity) {
        val now = LocalDateTime.now()
        namedParameterJdbcTemplate.jdbcTemplate.update(
            "insert into social_network.users (id, login, password, name, surname, age, gender, city, create_date, modify_date) values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
            userEntity.id,
            userEntity.login,
            userEntity.password,
            userEntity.name,
            userEntity.surname,
            userEntity.age,
            userEntity.gender,
            userEntity.city,
            now,
            now
        )
        val batch: Array<SqlParameterSource> = userEntity.interests.map { interest ->
            MapSqlParameterSource()
                .addValue(ID, userEntity.id, Types.VARCHAR)
                .addValue(INTEREST, interest, Types.VARCHAR)
        }.toTypedArray()
        namedParameterJdbcTemplate.batchUpdate(
            // language=sql
            """
            insert into social_network.interests (id, interest) values (:id, :interest)
           """.trimIndent(),
            batch
        )
    }

    override fun findByIdOrNull(id: String): UserEntity? {
        val users: List<UserEntity> =
            namedParameterJdbcOperations.query(
                "select * from social_network.users where id = :id",
                mapOf(ID to id),
                userRowMapper
            )
        val interests =
            namedParameterJdbcTemplate.jdbcTemplate.queryForList(
                "select interest from social_network.interests where id = ?",
                String::class.java,
                id
            )
        return users.takeIf { it.isNotEmpty() }?.let {
            it[0].interests.addAll(interests)
            it[0]
        }
    }

    override fun deleteById(id: String) {
        namedParameterJdbcOperations.update(
            "delete from social_network.interests where id = :id",
            mapOf("id" to id)
        )
        namedParameterJdbcOperations.update("delete from social_network.users where id = :id", mapOf(ID to id))
    }

    override fun getByLogin(login: String): UserEntity? {
        val user: List<UserEntity> =
            namedParameterJdbcOperations.query(
                "select * from social_network.users where login = :login",
                mapOf(LOGIN to login),
                userRowMapper
            )

        return user.takeIf { it.isNotEmpty() }?.let {
            val interests =
                namedParameterJdbcTemplate.jdbcTemplate.queryForList(
                    "select interest from social_network.interests where id = ?",
                    String::class.java,
                    it[0].id
                )
            it[0].interests.addAll(interests)
            it[0]
        }
    }

    private val userRowMapper = RowMapper { resultSet: ResultSet, i: Int ->
        UserEntity(
            resultSet.getString(ID),
            resultSet.getString(LOGIN),
            resultSet.getString(PASSWORD),
            resultSet.getString(NAME),
            resultSet.getString(SURNAME),
            resultSet.getInt(AGE),
            resultSet.getString(GENDER),
            mutableSetOf(),
            resultSet.getString(CITY),
            resultSet.getTimestamp(CREATED).toLocalDateTime(),
            resultSet.getTimestamp(MODIFIED).toLocalDateTime(),
        )
    }
}
