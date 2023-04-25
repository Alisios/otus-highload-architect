package ru.otus.highload.chats.core.db

import org.springframework.jdbc.core.RowMapper
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.stereotype.Service
import java.sql.ResultSet
import java.time.LocalDateTime

@Service
class ChatRepositoryJdbc(
    private val namedParameterJdbcOperations: NamedParameterJdbcOperations,
    private val namedParameterJdbcTemplate: NamedParameterJdbcTemplate
) : ChatRepository {

    private companion object {
        const val MESSAGE_ID = "message_id"
        const val CHAT_ID = "chat_id"
        const val FROM_USER_ID = "from_user_id"
        const val TO_USER_ID = "to_user_id"
        const val TEXT = "message_text"
        const val CREATED = "create_date"
    }

    override fun saveMessage(message: MessageDtoEntity) {
        val now = LocalDateTime.now()
        namedParameterJdbcTemplate.jdbcTemplate.update(
            "insert into social_network.messages (message_id, chat_id, from_user_id, to_user_id, message_text, create_date) values(?, ?, ?, ?, ?, ?)" +
                    "on conflict (message_id, chat_id) do nothing ",
            message.messageId,
            message.chatId,
            message.fromUserId,
            message.toUserId,
            message.text,
            now
        )
    }

    override fun saveChat(friendChatEntity: FriendChatEntity) {
        val now = LocalDateTime.now()
        namedParameterJdbcTemplate.jdbcTemplate.update(
            "insert into social_network.chats (chat_id, from_user_id, to_user_id, create_date) values(?, ?, ?, ?)" +
                    "on conflict (chat_id) do nothing ",
            friendChatEntity.chatId,
            friendChatEntity.fromUserId,
            friendChatEntity.toUserId,
            now
        )
    }

    override fun ifChatExist(chatId: String): Boolean =
        namedParameterJdbcOperations.query(
            "select * from social_network.chats where chat_id = :chat_id",
            mapOf(CHAT_ID to chatId),
            chatRowMapper
        ).size > 0

    override fun getChatByChatId(chatId: String): FriendChatEntity? {
        val chat = namedParameterJdbcOperations.query(
            "select * from social_network.chats where chat_id = :chat_id",
            mapOf(CHAT_ID to chatId),
            chatRowMapper
        )
        return chat.takeIf { it.isNotEmpty() }?.get(0)
    }

    override fun getMessagesByChatId(chatId: String): MutableList<MessageDtoEntity> =
        namedParameterJdbcOperations.query(
            "select * from social_network.messages where chat_id = :chat_id order by create_date DESC LIMIT 30;",
            mapOf(CHAT_ID to chatId),
            messageRowMapper
        )

    override fun getAllChatsByUserId(userId: String): MutableList<FriendChatEntity> {
        return namedParameterJdbcOperations.query(
            "select * from social_network.chats where from_user_id = :from_user_id order by create_date DESC LIMIT 30;",
            mapOf(FROM_USER_ID to userId),
            chatRowMapper
        )
    }

    private val messageRowMapper = RowMapper { resultSet: ResultSet, i: Int ->
        MessageDtoEntity(
            messageId = resultSet.getString(MESSAGE_ID),
            chatId = resultSet.getString(CHAT_ID),
            fromUserId = resultSet.getString(FROM_USER_ID),
            toUserId = resultSet.getString(TO_USER_ID),
            text = resultSet.getString(TEXT),
            created = resultSet.getTimestamp(CREATED).toLocalDateTime(),
        )
    }
    private val chatRowMapper = RowMapper { resultSet: ResultSet, i: Int ->
        FriendChatEntity(
            chatId = resultSet.getString(CHAT_ID),
            fromUserId = resultSet.getString(FROM_USER_ID),
            toUserId = resultSet.getString(TO_USER_ID),
            created = resultSet.getTimestamp(CREATED).toLocalDateTime(),
        )
    }
}
