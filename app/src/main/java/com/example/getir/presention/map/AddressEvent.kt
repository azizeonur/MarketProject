package com.example.getir.presention.map

import com.example.getir.domain.address.Address


sealed class AddressEvent {

    object LoadAddresses : AddressEvent()

    data class SelectAddress(val address: Address) : AddressEvent()

    data class SaveAddress(val address: Address) : AddressEvent()

    data class DeleteAddress(val addressId: String) : AddressEvent()

    data class UpdateAddress(val address: Address) : AddressEvent()

    object ClearMessage : AddressEvent()
}