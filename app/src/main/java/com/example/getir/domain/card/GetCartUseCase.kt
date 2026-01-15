package com.example.getir.domain.card

import javax.inject.Inject


class GetCartUseCase @Inject constructor(
    private val cartRepository: CartRepository
){
    operator fun invoke() = cartRepository.getCart()

}