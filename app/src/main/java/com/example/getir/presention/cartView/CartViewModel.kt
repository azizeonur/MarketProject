package com.example.getir.presention.cartView

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.getir.domain.card.AddToCartUseCase
import com.example.getir.domain.card.GetCartUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val getCartUseCase: GetCartUseCase,
    private val addToCartUseCase: AddToCartUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(CartUiState())
    val uiState: StateFlow<CartUiState> = _uiState

    init {
        observeCart()
    }

    private fun observeCart() {
        viewModelScope.launch {
            getCartUseCase().collect { items ->
                val total = items.sumOf { it.price * it.quantity }
                _uiState.value = CartUiState(
                    items = items,
                    totalPrice = total
                )
            }
        }
    }

    fun onEvent(event: CartEvent) {
        when (event) {
            is CartEvent.AddProduct -> {
                viewModelScope.launch {
                    addToCartUseCase(event.item)
                }
            }
        }
    }
}