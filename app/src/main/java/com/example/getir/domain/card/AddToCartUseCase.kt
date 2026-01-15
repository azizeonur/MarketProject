package com.example.getir.domain.card

import javax.inject.Inject

class AddToCartUseCase @Inject constructor(
    private val repository: CartRepository
) {
    suspend operator fun invoke(item: CartItem) {
        repository.addToCart(item)
    }
}