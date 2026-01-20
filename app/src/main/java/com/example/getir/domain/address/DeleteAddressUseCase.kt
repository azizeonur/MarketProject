package com.example.getir.domain.address

import com.example.getir.domain.address.AddressRepository
import javax.inject.Inject

class DeleteAddressUseCase  @Inject constructor(
    private val repository: AddressRepository
) {
    suspend operator fun invoke(addressId: String) {
        repository.deleteAddress(addressId)
    }
}