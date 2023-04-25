package ru.otus.highload.posts.core.db.persistence

import org.springframework.jdbc.core.RowMapper
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.stereotype.Service
import java.sql.ResultSet
import java.time.LocalDateTime

@Service
class PostRepositoryJdbc(
    private val namedParameterJdbcOperations: NamedParameterJdbcOperations,
    private val namedParameterJdbcTemplate: NamedParameterJdbcTemplate
) : PostRepository {

    private companion object {
        const val POST_ID = "post_id"
        const val USER_ID = "user_id"
        const val POST_TEXT = "post_text"
        const val CREATED = "create_date"
        const val MODIFIED = "modify_date"
    }

    override fun upsertForUtility(postEntity: PostEntity) {
        namedParameterJdbcTemplate.jdbcTemplate.update(
            "insert into social_network.posts (post_id, user_id, post_text, create_date, modify_date) values(?, ?, ?, ?, ?)" +
                    "on conflict (post_id) do update set post_text = EXCLUDED.post_text, modify_date=now()",
            postEntity.postId,
            postEntity.userId,
            postEntity.postText,
            postEntity.createDate,
            postEntity.modifyDate
        )
    }

    override fun upsert(postEntity: PostEntity) {
        val now = LocalDateTime.now()
        namedParameterJdbcTemplate.jdbcTemplate.update(
            "insert into social_network.posts (post_id, user_id, post_text, create_date, modify_date) values(?, ?, ?, ?, ?)" +
                    "on conflict (post_id) do update set post_text = EXCLUDED.post_text, modify_date=now()",
            postEntity.postId,
            postEntity.userId,
            postEntity.postText,
            now,
            now
        )
    }

    override fun findByIdOrNull(postId: String): PostEntity? {
        val posts: List<PostEntity> =
            namedParameterJdbcOperations.query(
                "select * from social_network.posts where post_id = :post_id",
                mapOf(POST_ID to postId),
                postRowMapper
            )
        return posts.takeIf { it.isNotEmpty() }?.get(0)
    }

    override fun findByUserId(userId: String): MutableList<PostEntity> {
        return namedParameterJdbcOperations.query(
            "select * from social_network.posts where user_id = :user_id order by modify_date DESC LIMIT 30;",
            mapOf(USER_ID to userId),
            postRowMapper
        )
    }

    override fun deleteById(postId: String) {
        namedParameterJdbcOperations.update(
            "delete from social_network.posts where post_id = :post_id",
            mapOf(POST_ID to postId)
        )
    }

    override fun deleteByUserId(userId: String) {
        namedParameterJdbcOperations.update(
            "delete from social_network.posts where user_id = :user_id",
            mapOf(USER_ID to userId)
        )
    }

    override fun getFeed(userId: String, offset: Long?, limit: Long?): MutableList<PostEntity> {
        return namedParameterJdbcOperations.query(
            "select * from social_network.posts as p " +
                    "left join social_network.friends as f on p.user_id = f.friend_id " +
                    "where f.user_id = :user_id " +
                    "order by p.modify_date desc " +
                    "offset :offset " +
                    "limit :limit",
            mapOf(USER_ID to userId, "limit" to limit, "offset" to offset),
            postRowMapper
        )
    }


    private val postRowMapper = RowMapper { resultSet: ResultSet, i: Int ->
        PostEntity(
            postId = resultSet.getString(POST_ID),
            userId = resultSet.getString(USER_ID),
            postText = resultSet.getString(POST_TEXT),
            createDate = resultSet.getTimestamp(CREATED).toLocalDateTime(),
            modifyDate = resultSet.getTimestamp(MODIFIED).toLocalDateTime(),
        )
    }
}
