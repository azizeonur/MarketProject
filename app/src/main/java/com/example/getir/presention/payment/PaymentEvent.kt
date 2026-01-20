package com.example.getir.presention.payment

import com.example.getir.domain.payment.CardInfo
import com.example.getir.domain.payment.Payment

sealed class PaymentEvent {
    object ProcessPayment : PaymentEvent()
    object ClearMessage : PaymentEvent()
    data class UpdateCardInfo(val cardInfo: CardInfo) : PaymentEvent()

}