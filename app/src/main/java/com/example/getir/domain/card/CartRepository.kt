package com.example.getir.domain.card

import kotlinx.coroutines.flow.Flow

interface CartRepository {
    fun getCart(): Flow<List<CartItem>>
    suspend fun addToCart(item: CartItem)
    suspend fun clearCart()
    suspend fun removeProduct(productId: String)
}