package com.example.getir.domain.address

data class Address(
    val id: String,
    val userId: String,
    val title: String,
    val fullAddress: String,
    val lat: Double,
    val lng: Double,
    val isDefault: Boolean
)