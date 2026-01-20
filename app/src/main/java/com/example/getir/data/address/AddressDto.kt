package com.example.getir.data.address

import com.example.getir.domain.address.Address

data class AddressDto(
    val id: String?,
    val userId: String,
    val title: String,
    val fullAddress: String,
    val lat: Double,
    val lng: Double,
    val isDefault: Boolean
)


fun AddressDto.toDomain() = Address(
    id = id.orEmpty(),
    userId = userId,
    title = title,
    fullAddress = fullAddress,
    lat = lat,
    lng = lng,
    isDefault = isDefault
)

fun Address.toDto() = AddressDto(
    id = id,
    userId = userId,
    title = title,
    fullAddress = fullAddress,
    lat = lat,
    lng = lng,
    isDefault = isDefault
)