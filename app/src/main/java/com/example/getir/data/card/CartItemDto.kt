package com.example.getir.data.card

import android.R.attr.name
import com.example.getir.domain.card.CartItem

data class CartItemDto(
    val product_id: String,
    val name: String,
    val price: Double,
    val quantity: Int
)

fun CartItemDto.toDomain(): CartItem {
    return CartItem(
        productId = product_id,
        name = name,
        price = price,
        quantity = quantity
    )
}

fun CartItem.toDto(): CartItemDto {
    return CartItemDto(
        product_id = productId,
        name = name,
        price = price,
        quantity = quantity
    )
}