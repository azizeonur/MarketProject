package com.example.getir.data.payment


data class PaymentRequestDto(
    val amount: Double,
    val items: List<PaymentItemDto>,
    val addressId: String,
    val cardInfo: CardInfoDto
)

data class PaymentItemDto(
    val productId: String,
    val name: String,
    val price: Double,
    val quantity: Int
)

data class PaymentResponseDto(
    val success: Boolean,
    val message: String,
    val transactionId: String? = null
)
data class CardInfoDto(
    val cardNumber: String,
    val cardHolder: String,
    val expiryDate: String,
    val cvv: String
)