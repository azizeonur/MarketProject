package com.example.getir.domain.payment

interface PaymentRepository {
    suspend fun processPayment(payment: Payment): PaymentResult
}