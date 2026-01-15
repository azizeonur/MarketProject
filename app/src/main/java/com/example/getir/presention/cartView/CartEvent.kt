package com.example.getir.presention.cartView

import com.example.getir.domain.card.CartItem

sealed class CartEvent {
    data class AddToCart(val item: CartItem) : CartEvent()
    object Checkout : CartEvent()
}
