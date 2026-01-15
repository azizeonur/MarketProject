package com.example.getir.data.checkout

import com.example.getir.data.product.ProductApi
import com.example.getir.domain.card.CartItem
import com.example.getir.domain.checkout.OrderRepository
import javax.inject.Inject

class OrderRepositoryImpl @Inject constructor(
    private val api: ProductApi
) : OrderRepository {

    override suspend fun createOrder(
        cartItems: List<CartItem>
    ): Result<Unit> {
        return try {
            api.createOrder(
                OrderRequest(
                    items = cartItems.map {
                        OrderItemDto(
                            productId = it.productId,
                            quantity = it.quantity
                        )
                    }
                )
            )
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}