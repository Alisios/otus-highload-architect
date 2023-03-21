package ru.otus.highload.domain.users.infrastructure.persistence.post

import ru.otus.highload.domain.users.infrastructure.persistence.post.entity.PostEntity

interface PostRepository {

    fun upsert(postEntity: PostEntity)

    fun findByIdOrNull(postId: String): PostEntity?

    fun findByUserId(userId: String): List<PostEntity>

    fun deleteById(postId: String)

    fun deleteByUserId(userId: String)

    fun getFeed(userId: String, offset: Long = 0, number: Long = 10): List<PostEntity>
}
