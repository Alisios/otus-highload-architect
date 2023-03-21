package ru.otus.highload.domain.users.infrastructure.persistence.post

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional
import ru.otus.highload.domain.users.core.model.PostDto
import ru.otus.highload.domain.users.core.model.toDto
import ru.otus.highload.domain.users.core.model.toEntity
import ru.otus.highload.domain.users.core.ports.out.PostPersistencePort

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
    override fun getByUserId(userId: String): List<PostDto> =
        postRepository.findByUserId(userId).map { it.toDto() }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    override fun getFriendsFeed(userId: String, offset: Long, number: Long): List<PostDto> =
        postRepository.getFeed(userId, offset, number).map { it.toDto() }

}
