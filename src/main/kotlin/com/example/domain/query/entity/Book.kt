package com.example.domain.query.entity

import java.math.BigDecimal

data class Book(
    val isbn: String,
    val authorIds: List<Long>,
    val title: String,
    val price: BigDecimal,
)
