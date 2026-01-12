package com.example.getir.domain.card

import CartRepository
import kotlinx.coroutines.flow.Flow

class GetCartUseCase(
    private val repository: CartRepository
) {
    operator fun invoke(): Flow<List<CartItem>> {
        return repository.getCart()
    }
}