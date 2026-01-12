package com.example.getir.data.card

import CartRepository
import com.example.getir.data.product.ProductApi
import com.example.getir.domain.card.CartItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class CartRepositoryImpl(
    private val api: ProductApi
) : CartRepository {

    override fun getCart(): Flow<List<CartItem>> = flow {
        val response = api.getCart()
        emit(response.map { it.toDomain() })
    }

    override suspend fun addToCart(item: CartItem) {
        api.addToCart(item.toDto())
    }
}