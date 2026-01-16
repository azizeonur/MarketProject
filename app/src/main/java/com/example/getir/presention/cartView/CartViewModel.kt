package com.example.getir.presention.cartView

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.getir.domain.card.AddToCartUseCase
import com.example.getir.domain.card.ClearCartUseCase
import com.example.getir.domain.card.GetCartUseCase
import com.example.getir.domain.card.RemoveFromCartUseCase
import com.example.getir.domain.checkout.CheckoutUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class CartViewModel @Inject constructor(
    private val getCartUseCase: GetCartUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(CartUiState())
    val uiState: StateFlow<CartUiState> = _uiState

    init {
        observeCart()
    }

    private fun observeCart() {
        viewModelScope.launch {
            getCartUseCase().collect { items ->
                _uiState.value = _uiState.value.copy(
                    items = items,
                    totalPrice = items.sumOf { it.price * it.quantity }
                )
            }
        }
    }

    fun onEvent(event: CartEvent) {
        when (event) {

            is CartEvent.AddToCart -> {
                val currentItems = _uiState.value.items.toMutableList()
                val index = currentItems.indexOfFirst {
                    it.productId == event.item.productId
                }

                if (index >= 0) {
                    val item = currentItems[index]
                    currentItems[index] =
                        item.copy(quantity = item.quantity + 1)
                } else {
                    currentItems.add(event.item)
                }

                _uiState.value = _uiState.value.copy(
                    items = currentItems,
                    totalPrice = _uiState.value.totalPrice + event.item.price
                )
            }

            is CartEvent.RemoveFromCart -> {
                val currentItems = _uiState.value.items.toMutableList()
                val index = currentItems.indexOfFirst {
                    it.productId == event.productId
                }

                if (index >= 0) {
                    val item = currentItems[index]

                    if (item.quantity > 1) {
                        currentItems[index] =
                            item.copy(quantity = item.quantity - 1)
                    } else {
                        currentItems.removeAt(index)
                    }

                    _uiState.value = _uiState.value.copy(
                        items = currentItems,
                        totalPrice = _uiState.value.totalPrice - item.price
                    )
                }
            }

            CartEvent.Checkout -> {
                viewModelScope.launch {
                    _uiState.value = _uiState.value.copy(
                        isLoading = true,
                        error = null
                    )

                }
            }
        }
    }
}