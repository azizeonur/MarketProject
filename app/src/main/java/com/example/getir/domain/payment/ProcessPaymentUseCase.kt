package com.example.getir.domain.payment

import javax.inject.Inject

class ProcessPaymentUseCase @Inject constructor(
    private val repository: PaymentRepository
) {
    suspend operator fun invoke(payment: Payment): PaymentResult {
        return repository.processPayment(payment)
    }
}