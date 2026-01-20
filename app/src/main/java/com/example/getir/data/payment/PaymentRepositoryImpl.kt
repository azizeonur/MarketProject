package com.example.getir.data.payment

import com.example.getir.data.product.ProductApi
import com.example.getir.domain.payment.Payment
import com.example.getir.domain.payment.PaymentRepository
import com.example.getir.domain.payment.PaymentResult
import javax.inject.Inject

class PaymentRepositoryImpl @Inject constructor(
    private val api: ProductApi
) : PaymentRepository {
    override suspend fun processPayment(payment: Payment): PaymentResult {
        val request = PaymentRequestDto(
            amount = payment.amount,
            items = payment.items.map { item ->
                PaymentItemDto(
                    productId = item.productId,
                    name = item.name,
                    price = item.price,
                    quantity = item.quantity
                )
            },
            addressId = payment.addressId,
            cardInfo = CardInfoDto(
                cardNumber = payment.cardInfo.cardNumber,
                cardHolder = payment.cardInfo.cardHolder,
                expiryDate = payment.cardInfo.expiryDate,
                cvv = payment.cardInfo.cvv
            )
        )

        val response = api.processPayment(request)

        return PaymentResult(
            success = response.success,
            message = response.message,
            transactionId = response.transactionId
        )
    }
}