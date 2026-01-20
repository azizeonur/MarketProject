package com.example.getir.domain.address

import javax.inject.Inject

class UpdateAddressUseCase  @Inject constructor(
    private val repository: AddressRepository
) {
    suspend operator fun invoke(address: Address) {
        repository.updateAddress(address)
    }
}