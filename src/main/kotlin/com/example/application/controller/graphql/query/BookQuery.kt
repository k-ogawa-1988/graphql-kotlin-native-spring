package com.example.application.controller.graphql.query

import com.example.application.controller.graphql.type.Book
import com.example.application.controller.graphql.type.toApp
import com.example.domain.query.repository.BookRepository
import com.expediagroup.graphql.server.operations.Query
import org.springframework.stereotype.Component

@Component
class BookQuery(private val bookRepository: BookRepository) : Query {
    @Suppress("unused")
    suspend fun listBooks(): List<Book> {
        return bookRepository.findAll().map { it.toApp() }
    }

    @Suppress("unused")
    suspend fun findBooks(keyword: String): List<Book> {
        return bookRepository.find(keyword).map { it.toApp() }
    }
}
