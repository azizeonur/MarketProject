package com.example.getir.domain.payment

data class CardInfo(
    val cardNumber: String,
    val cardHolder: String,
    val expiryDate: String,
    val cvv: String
)

data class Payment(
    val amount: Double,
    val items: List<PaymentItem>,
    val addressId: String,
    val cardInfo: CardInfo
)

data class PaymentItem(
    val productId: String,
    val name: String,
    val price: Double,
    val quantity: Int
)

data class PaymentResult(
    val success: Boolean,
    val message: String,
    val transactionId: String? = null
)