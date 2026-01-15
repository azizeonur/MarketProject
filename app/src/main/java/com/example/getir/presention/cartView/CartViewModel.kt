package com.example.getir.presention.cartView

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.getir.domain.card.AddToCartUseCase
import com.example.getir.domain.card.ClearCartUseCase
import com.example.getir.domain.card.GetCartUseCase
import com.example.getir.domain.checkout.CheckoutUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class CartViewModel @Inject constructor(
    private val getCartUseCase: GetCartUseCase,
    private val addToCartUseCase: AddToCartUseCase,
    private val checkoutUseCase: CheckoutUseCase,
    private val clearCartUseCase: ClearCartUseCase
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
                viewModelScope.launch {
                    addToCartUseCase(event.item)
                }
            }

            CartEvent.Checkout -> {
                viewModelScope.launch {
                    _uiState.value = _uiState.value.copy(
                        isLoading = true,
                        error = null
                    )

                    val result = checkoutUseCase(_uiState.value.items)

                    result
                        .onSuccess {
                            clearCartUseCase()
                            _uiState.value = _uiState.value.copy(
                                isLoading = false,
                                isOrderSuccess = true
                            )
                        }
                        .onFailure {
                            _uiState.value = _uiState.value.copy(
                                isLoading = false,
                                error = it.message ?: "Sipariş başarısız"
                            )
                        }
                }
            }
        }
    }
}