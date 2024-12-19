package com.example.domain.query.repository

import com.example.domain.query.entity.Book

interface BookRepository {
    suspend fun findAll(): List<Book>
    suspend fun find(keyword: String): List<Book>
}
