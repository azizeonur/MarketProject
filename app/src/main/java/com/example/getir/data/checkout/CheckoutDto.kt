package com.example.getir.data.checkout

data class OrderItemDto(
    val productId: String,
    val quantity: Int
)

data class OrderRequest(
    val items: List<OrderItemDto>
)