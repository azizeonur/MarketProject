package com.example.getir.domain.checkout

import com.example.getir.domain.card.CartItem

interface OrderRepository {

    suspend fun createOrder(
        items: List<CartItem>
    ): Result<Unit>
}