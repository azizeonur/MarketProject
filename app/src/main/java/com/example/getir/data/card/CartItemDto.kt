package com.example.getir.data.card

import android.R.attr.name
import com.example.getir.domain.card.CartItem
import com.google.gson.annotations.SerializedName


data class CartItemDto(

    val productId: String,

    val name: String,
    val price: Double,
    val quantity: Int,
    val imageUrl: String
)

fun CartItemDto.toDomain(): CartItem {
    return CartItem(
        productId = productId,
        name = name,
        price = price,
        quantity = quantity,
        imageUrl = imageUrl
    )
}

fun CartItem.toDto(): CartItemDto {
    return CartItemDto(
        productId = productId,
        name = name,
        price = price,
        quantity = quantity,
        imageUrl = imageUrl
    )
}