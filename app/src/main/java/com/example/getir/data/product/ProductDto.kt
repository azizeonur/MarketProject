package com.example.getir.data.product

import com.example.getir.domain.product.Product

data class ProductDto(
    val id: String,
    val name: String,
    val price: Double,
    val categoryId: String
)
fun ProductDto.toDomain(): Product {
    return Product(
        id = id,
        name = name,
        price = price
    )
}