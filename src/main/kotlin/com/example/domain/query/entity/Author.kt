package com.example.domain.query.entity

import com.example.domain.enum_.AuthorType

data class Author(
    val id: Long,
    val name: String,
    val type: AuthorType,
)
