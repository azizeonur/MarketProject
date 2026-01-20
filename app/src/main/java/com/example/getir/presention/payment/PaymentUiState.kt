package com.example.getir.presention.payment

import com.example.getir.domain.payment.CardInfo
import com.example.getir.domain.payment.PaymentItem

data class PaymentUiState(
    val amount: Double = 0.0,
    val items: List<PaymentItem> = emptyList(),
    val addressId: String = "",
    val cardInfo: CardInfo = CardInfo("", "", "", ""),
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val transactionId: String? = null,
    val error: String? = null
)