package com.example.getir.data.card

import com.example.getir.data.product.ProductApi
import com.example.getir.domain.card.CartRepository
import com.example.getir.domain.card.CartItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


class CartRepositoryImpl @Inject constructor(
    private val api: ProductApi
) : CartRepository {

    override fun getCart(): Flow<List<CartItem>> = flow {
        emit(api.getCart().map { it.toDomain() })
    }

    override suspend fun addToCart(item: CartItem) {
        api.addToCart(item.toDto())
    }
    override suspend fun clearCart() {
        api.clearCart()
    }
}