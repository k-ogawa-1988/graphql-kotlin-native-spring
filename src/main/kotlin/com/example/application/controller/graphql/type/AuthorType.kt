package com.example.application.controller.graphql.type

import com.example.domain.enum_.AuthorType
import com.expediagroup.graphql.generator.scalars.ID

data class Author(
    val id: ID,
    val name: String,
    val type: AuthorType,
)

fun com.example.domain.query.entity.Author.toApp() = Author(
    id = ID(id.toString()),
    name = name,
    type = type,
)
