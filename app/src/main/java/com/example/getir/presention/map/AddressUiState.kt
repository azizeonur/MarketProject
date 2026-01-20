package com.example.getir.presention.map

import com.example.getir.domain.address.Address


data class AddressUiState(
    val isLoading: Boolean = false,
    val addresses: List<Address> = emptyList(),
    val selectedAddress: Address? = null,
    val error: String? = null,
    val isSaved: Boolean = false,
    val selectedLat: Double? = null,
    val selectedLng: Double? = null
)