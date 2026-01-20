package com.example.getir.presention.payment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.getir.domain.address.AddressRepository
import com.example.getir.domain.card.CartRepository
import com.example.getir.domain.payment.Payment
import com.example.getir.domain.payment.PaymentItem
import com.example.getir.domain.payment.ProcessPaymentUseCase
import com.example.getir.presention.cartView.CartViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlinx.coroutines.flow.combine

@HiltViewModel
class PaymentViewModel @Inject constructor(
    private val processPayment: ProcessPaymentUseCase,
    private val cartRepository: CartRepository,
    private val addressRepository: AddressRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(PaymentUiState())
    val uiState: StateFlow<PaymentUiState> = _uiState

    init {
        observeCartAndAddress()
    }

    private fun observeCartAndAddress() {
        viewModelScope.launch {
            combine(
                cartRepository.getCart(),
                addressRepository.getSelectedAddress()
            ) { cartItems, selectedAddress ->

                val items = cartItems.map {
                    PaymentItem(
                        productId = it.productId,
                        name = it.name,
                        price = it.price,
                        quantity = it.quantity
                    )
                }

                PaymentUiState(
                    items = items,
                    amount = items.sumOf { it.price * it.quantity },
                    addressId = selectedAddress?.id.orEmpty()
                )
            }.collect { paymentState ->
                _uiState.value = paymentState
            }
        }
    }

    fun onEvent(event: PaymentEvent) {
        when (event) {
            PaymentEvent.ProcessPayment -> processPaymentRequest(buildPayment())
            PaymentEvent.ClearMessage -> _uiState.update { it.copy(error = null, isSuccess = false) }
            is PaymentEvent.UpdateCardInfo -> _uiState.update { it.copy(cardInfo = event.cardInfo) }
        }
    }
    fun resetPaymentState() {
        _uiState.update { it.copy(isSuccess = false, error = null) }
    }

    private fun buildPayment(): Payment {
        val state = _uiState.value
        return Payment(
            amount = state.amount,
            items = state.items,
            addressId = state.addressId,
            cardInfo = state.cardInfo
        )
    }

    private fun processPaymentRequest(payment: Payment) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            runCatching { processPayment(payment) }
                .onSuccess { result ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            isSuccess = result.success,
                            transactionId = result.transactionId,
                            error = if (!result.success) result.message else null
                        )
                    }
                }
                .onFailure { e ->
                    _uiState.update { it.copy(isLoading = false, error = e.message) }
                }
        }
    }
}
