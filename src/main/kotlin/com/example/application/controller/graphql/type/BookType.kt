package com.example.application.controller.graphql.type

import com.example.application.controller.graphql.dataloader.AuthorDataLoader
import com.expediagroup.graphql.generator.scalars.ID
import graphql.schema.DataFetchingEnvironment
import java.math.BigDecimal

data class Book(
    val isbn: ID,
    val authorIds: List<ID>,
    val title: String,
    val price: BigDecimal,
) {
    @Suppress("unused")
    fun authors(dfe: DataFetchingEnvironment) = AuthorDataLoader.getValues(dfe, authorIds)
}

fun com.example.domain.query.entity.Book.toApp() = Book(
    isbn = ID(isbn),
    authorIds = authorIds.map { ID(it.toString()) },
    title = title,
    price = price,
)
