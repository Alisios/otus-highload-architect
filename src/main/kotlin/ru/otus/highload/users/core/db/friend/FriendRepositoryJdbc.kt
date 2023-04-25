package ru.otus.highload.users.core.db.friend

import org.springframework.jdbc.core.RowMapper
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional
import java.sql.ResultSet
import java.time.LocalDateTime

@Service
class FriendRepositoryJdbc(
    private val namedParameterJdbcOperations: NamedParameterJdbcOperations,
    private val namedParameterJdbcTemplate: NamedParameterJdbcTemplate
) : FriendRepository {

    private companion object {
        const val USER_ID = "user_id"
        const val FRIEND_ID = "friend_id"
        const val CREATED = "create_date"
    }

    override fun addFriend(friendEntity: FriendEntity) {
        val now = LocalDateTime.now()
        namedParameterJdbcTemplate.jdbcTemplate.update(
            "insert into social_network.friends (user_id, friend_id, create_date) values(?, ?, ?) on conflict (user_Id, friend_id) do nothing",
            friendEntity.userId,
            friendEntity.friendId,
            now
        )
    }


    override fun getAllFriends(userId: String): List<FriendEntity> {
        return namedParameterJdbcOperations.query(
            "select * from social_network.friends where user_id = :user_id order by create_date DESC;",
            mapOf(USER_ID to userId),
            friendRowMapper
        )
    }

    override fun getAllUsersByFriendId(friendId: String): List<FriendEntity> {
        return namedParameterJdbcOperations.query(
            "select * from social_network.friends where friend_id = :friend_id order by create_date DESC;",
            mapOf(FRIEND_ID to friendId),
            friendRowMapper
        )
    }

    override fun deleteFriend(userId: String, friendId: String) {
        namedParameterJdbcOperations.update(
            "delete from social_network.friends where (user_id = :user_id and friend_id = :friend_id)",
            mapOf(USER_ID to userId, FRIEND_ID to friendId),
        )
    }

    private val friendRowMapper = RowMapper { resultSet: ResultSet, i: Int ->
        FriendEntity(
            userId = resultSet.getString(USER_ID),
            friendId = resultSet.getString(FRIEND_ID),
            createDate = resultSet.getTimestamp(CREATED).toLocalDateTime(),
        )
    }
}
