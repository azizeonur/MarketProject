package com.example.getir.presention.cartView

import com.example.getir.domain.card.CartItem

sealed interface CartEvent {
    data class AddToCart(val item: CartItem) : CartEvent
    data class RemoveFromCart(val productId: String) : CartEvent
    object Checkout : CartEvent
    object ClearMessage : CartEvent
    object ClearCart : CartEvent
}