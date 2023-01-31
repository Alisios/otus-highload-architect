////package ru.otus.highload.domain.users.infrastructure.persistence.entity
////
////import org.springframework.data.repository.CrudRepository
////import java.util.UUID
////
////interface UserRepository : CrudRepository<UserEntity, UUID>
//
//package ru.otus.highload.domain.users.infrastructure.persistence.entity
//
//import org.springframework.jdbc.core.JdbcTemplate
//import org.springframework.jdbc.core.RowMapper
//import org.springframework.jdbc.core.namedparam.MapSqlParameterSource
//import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations
//import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
//import org.springframework.jdbc.core.namedparam.SqlParameterSource
//import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils
//import org.springframework.jdbc.core.queryForList
//import org.springframework.stereotype.Service
//import ru.otus.highload.domain.users.infrastructure.persistence.UserRepository
//import java.sql.ResultSet
//import java.time.LocalDateTime
//import java.util.*
//import java.util.Map
//import kotlin.collections.List
//import kotlin.collections.emptySet
//import kotlin.collections.forEach
//import kotlin.collections.isNotEmpty
//
//
//@Service
//class UserRepositoryJdbc(
//    private val namedParameterJdbcOperations: NamedParameterJdbcOperations,
//    private val namedParameterJdbcTemplate: NamedParameterJdbcTemplate
//) : UserRepository {
//
//    private companion object {
//        const val ID = "id"
//        const val NAME = "name"
//        const val SURNAME = "surname"
//        const val AGE = "age"
//        const val CITY = "city"
//        const val CREATED = "create_date"
//        const val MODIFIED = "modify_date"
//    }
//
//    override fun save(userEntity: UserEntity) {
//        val now = LocalDateTime.now()
//        val params = MapSqlParameterSource()
//        params.addValue(ID, userEntity.id)
//        params.addValue(NAME, userEntity.name)
//        params.addValue(SURNAME, userEntity.surname)
//        params.addValue(AGE, userEntity.age)
//        params.addValue(CITY, userEntity.city)
//        params.addValue(MODIFIED, now)
//        params.addValue(CREATED, now)
//        val id = namedParameterJdbcOperations.query(
//            "insert into social_network.users (id, name, surname, age, city) values(:id,:name,:surname, :age, :city) returning id",
//            params
//        ) { resultSet: ResultSet, _: Int ->
//            resultSet.getLong(ID)
//        }[0]
//        val batch: Array<SqlParameterSource> = SqlParameterSourceUtils.createBatch(userEntity.interests.toTypedArray())
//        namedParameterJdbcTemplate.batchUpdate(
//            // language=sql
//            """
//            insert into social_network.interests (id, interest) values (:id, :it)
//           """.trimIndent(), batch
//        )
//        //namedParameterJdbcTemplate.jdbcTemplate.queryForMap("select * from social_network.users where id = '$id'".trimIndent())
//    }
//
//    override fun findByIdOrNull(id: UUID): UserEntity? {
//        val users: List<UserEntity> =
//            namedParameterJdbcOperations.query(
//                // language=sql
//                """
//                select * from social_network.users where id = :id
//                """.trimIndent(),
//                Map.of(ID, id),
//                userRowMapper
//            )
//        val interests =
//            namedParameterJdbcTemplate.jdbcTemplate.queryForList<String>("select * from social_network.interests where id = '$id'".trimIndent())
//        return users.takeIf { it.isNotEmpty() }?.let {
//            it[0].interests.plus(interests)
//            it[0]
//        }
//    }
//
//    private val userRowMapper = RowMapper { resultSet: ResultSet, i: Int ->
//        UserEntity(
//            resultSet.getObject(ID, UUID::class.java),
//            resultSet.getString(NAME),
//            resultSet.getString(SURNAME),
//            resultSet.getInt(AGE),
//            emptySet(),
//            resultSet.getString(CITY),
//            resultSet.getTimestamp(CREATED).toLocalDateTime(),
//            resultSet.getTimestamp(MODIFIED).toLocalDateTime(),
//        )
//    }
//}