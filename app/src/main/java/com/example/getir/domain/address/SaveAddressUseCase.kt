package com.example.getir.domain.address

import javax.inject.Inject

class SaveAddressUseCase  @Inject constructor(
    private val repository: AddressRepository
) {
    suspend operator fun invoke(address: Address) {
        require(address.userId.isNotBlank())
        require(address.fullAddress.isNotBlank())
        repository.saveAddress(address)
    }
}