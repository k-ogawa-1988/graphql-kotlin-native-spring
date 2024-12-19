package com.example.application.infrastructure.query.repository

import com.example.domain.enum_.AuthorType
import com.example.domain.query.entity.Author
import com.example.domain.query.repository.AuthorRepository
import org.springframework.stereotype.Repository

@Repository
class AuthorRepositoryImpl : AuthorRepository {
    companion object {
        private val data = mutableListOf(
            Author(
                id = 1,
                name = "Daniel Keyes",
                type = AuthorType.AUTHOR,
            ),
            Author(
                id = 2,
                name = "J. D. Salinger",
                type = AuthorType.AUTHOR,
            ),
            Author(
                id = 3,
                name = "野崎 孝",
                type = AuthorType.TRANSLATOR,
            ),
        )
    }

    override suspend fun findAll(): List<Author> {
        return data
    }

    override suspend fun findByIds(ids: List<Long>): List<Author> {
        return data.filter { ids.contains(it.id) }
    }
}
