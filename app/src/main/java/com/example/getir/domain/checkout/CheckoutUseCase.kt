package com.example.getir.domain.checkout

import com.example.getir.domain.card.CartItem
import javax.inject.Inject
import com.example.getir.domain.checkout.OrderRepository



class CheckoutUseCase @Inject constructor(
    private val orderRepository: OrderRepository
) {
    suspend operator fun invoke(cartItems: List<CartItem>): Result<Unit> {
        return orderRepository.createOrder(cartItems)
    }
}