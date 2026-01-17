package com.example.getir.presention.cartView

import com.example.getir.domain.card.CartItem


data class CartUiState(
    val items: List<CartItem> = emptyList(),
    val totalPrice: Double = 0.0,
    val isLoading: Boolean = false,
    val isOrderSuccess: Boolean = false,
    val error: String? = null
)