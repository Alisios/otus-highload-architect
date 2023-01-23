package ru.otus.highload.domain.users.infrastructure.persistence.entity

import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime
import java.util.UUID
import javax.persistence.CollectionTable
import javax.persistence.Column
import javax.persistence.ElementCollection
import javax.persistence.Entity
import javax.persistence.EntityListeners
import javax.persistence.FetchType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.Table

@Entity
@Table(name = "users")
@EntityListeners(AuditingEntityListener::class)
data class UserEntity(
    @Id
    val id: UUID,
    val name: String,
    val surname: String,
    val age: Int,

    @ElementCollection(targetClass = String::class, fetch = FetchType.EAGER)
    @CollectionTable(name = "interests", joinColumns = [JoinColumn(name = "id")])
    @Column(name = "interest")
    val interests: Set<String>,

    val city: String?,

    @CreatedDate
    @Column(updatable = false, nullable = false)
    var createDate: LocalDateTime? = null,

    @LastModifiedDate
    @Column(nullable = false)
    var modifyDate: LocalDateTime? = null
)