package com.example.getir.domain.product

data class Product(
    val id: String,
    val name: String,
    val price: Double,
    val imageUrl: String,
    val categoryId: String
)