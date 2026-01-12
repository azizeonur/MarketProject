package com.example.getir.domain.card

import CartRepository

class AddToCartUseCase(
    private val repository: CartRepository
) {
    suspend operator fun invoke(item: CartItem) {
        repository.addToCart(item)
    }
}