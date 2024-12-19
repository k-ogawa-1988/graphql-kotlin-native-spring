package com.example.application.infrastructure.query.repository

import com.example.domain.query.entity.Book
import com.example.domain.query.repository.BookRepository
import org.springframework.stereotype.Repository
import java.math.BigDecimal

@Repository
class BookRepositoryImpl : BookRepository {
    companion object {
        private val data = mutableListOf(
            Book(
                isbn = "978-0435123437",
                authorIds = listOf(1),
                title = "Flowers to Algernon",
                price = BigDecimal("3902"),
            ),
            Book(
                isbn = "978-0241950432",
                authorIds = listOf(2),
                title = "The Catcher in the Rye",
                price = BigDecimal("1923"),
            ),
            Book(
                isbn = "978-4770022479",
                authorIds = listOf(2, 3),
                title = "ライ麦畑でつかまえて (The Catcher in the Rye)",
                price = BigDecimal("1260"),
            ),
        )
    }

    override suspend fun findAll(): List<Book> {
        return data
    }

    override suspend fun find(keyword: String): List<Book> {
        return data.filter { it.isbn.lowercase().contains(keyword.lowercase()) || it.title.lowercase().contains(keyword.lowercase()) }
    }
}
