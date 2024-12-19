package com.example.domain.query.repository

import com.example.domain.query.entity.Author

interface AuthorRepository {
    suspend fun findAll(): List<Author>
    suspend fun findByIds(ids: List<Long>): List<Author>
}
