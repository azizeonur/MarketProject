package com.example.getir.presention.cartView

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.getir.domain.card.AddToCartUseCase
import com.example.getir.domain.card.CartItem
import com.example.getir.domain.card.ClearCartUseCase
import com.example.getir.domain.card.GetCartUseCase
import com.example.getir.domain.card.RemoveFromCartUseCase
import com.example.getir.domain.checkout.CheckoutUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class CartViewModel @Inject constructor() : ViewModel() {

    private val _uiState = MutableStateFlow(CartUiState())
    val uiState: StateFlow<CartUiState> = _uiState

    fun onEvent(event: CartEvent) {
        when (event) {

            is CartEvent.AddToCart -> {
                val items = _uiState.value.items.toMutableList()
                val index = items.indexOfFirst {
                    it.productId == event.item.productId
                }

                if (index >= 0) {
                    val item = items[index]
                    items[index] = item.copy(quantity = item.quantity + 1)
                } else {
                    items.add(event.item)
                }

                _uiState.value = _uiState.value.copy(
                    items = items,
                    totalPrice = items.sumOf { it.price * it.quantity }
                )
            }

            is CartEvent.RemoveFromCart -> {
                val items = _uiState.value.items.toMutableList()
                val index = items.indexOfFirst {
                    it.productId == event.productId
                }

                if (index >= 0) {
                    val item = items[index]
                    if (item.quantity > 1) {
                        items[index] = item.copy(quantity = item.quantity - 1)
                    } else {
                        items.removeAt(index)
                    }

                    _uiState.value = _uiState.value.copy(
                        items = items,
                        totalPrice = items.sumOf { it.price * it.quantity }
                    )
                }
            }

            CartEvent.Checkout -> {
                _uiState.value = _uiState.value.copy(
                    isLoading = true
                )

                viewModelScope.launch {
                    delay(1000)
                    _uiState.value = CartUiState(isOrderSuccess = true)
                }
            }

            else -> {}
        }
    }
}