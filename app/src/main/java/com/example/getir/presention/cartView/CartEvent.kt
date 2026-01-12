package com.example.getir.presention.cartView

import com.example.getir.domain.card.CartItem

sealed interface CartEvent {
    data class AddProduct(val item: CartItem) : CartEvent
}