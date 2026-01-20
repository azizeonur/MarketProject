package com.example.getir.domain.address

import javax.inject.Inject

class GetAddressByIdUseCase @Inject constructor(
    private val repository: AddressRepository
) {
    suspend operator fun invoke(addressId: String): Address {
        return repository.getAddressById(addressId)
    }
}