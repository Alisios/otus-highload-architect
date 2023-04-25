package ru.otus.highload.posts.core.db.persistence

interface PostRepository {

    fun upsertForUtility(postEntity: PostEntity)

    fun upsert(postEntity: PostEntity)

    fun findByIdOrNull(postId: String): PostEntity?

    fun findByUserId(userId: String): MutableList<PostEntity>

    fun deleteById(postId: String)

    fun deleteByUserId(userId: String)

    fun getFeed(userId: String, offset: Long? = 0, limit: Long? = 10): MutableList<PostEntity>
}
