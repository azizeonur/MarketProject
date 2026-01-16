package com.example.getir.domain.card

import javax.inject.Inject

class RemoveFromCartUseCase @Inject constructor(
    private val cartRepository: CartRepository
) {
    suspend operator fun invoke(productId: String) {
        cartRepository.removeProduct(productId)
    }
}