package ru.otus.highload.posts.core.db.persistence

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional
import ru.otus.highload.posts.core.model.PostDto
import ru.otus.highload.users.core.model.toDto
import ru.otus.highload.users.core.model.toEntity
import ru.otus.highload.posts.core.PostPersistencePort
import ru.otus.highload.posts.core.model.toDto
import ru.otus.highload.posts.core.model.toEntity

@Service
@Transactional
class PostPersistenceAdapter(private val postRepository: PostRepository) : PostPersistencePort {

    override fun addPost(postDto: PostDto) {
        postRepository.upsert(postDto.toEntity())
    }

    override fun updatePost(postDto: PostDto) {
        postRepository.upsert(postDto.toEntity())
    }

    override fun deleteByPostId(postId: String) {
        postRepository.deleteById(postId)
    }

    override fun deleteByUserId(userId: String) {
        postRepository.deleteByUserId(userId)
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    override fun getByPostId(postId: String): PostDto? =
        postRepository.findByIdOrNull(postId)?.toDto()


    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    override fun getByUserId(userId: String): MutableList<PostDto> =
        postRepository.findByUserId(userId).map { it.toDto() }.toMutableList()

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    override fun getFriendsFeed(userId: String, offset: Long?, number: Long?): MutableList<PostDto> =
        postRepository.getFeed(userId, offset, number).map { it.toDto() }.toMutableList()

}
