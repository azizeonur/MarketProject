package com.example.getir.domain.address

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetSelectedAddressUseCase @Inject constructor(
    private val repository: AddressRepository
) {
    operator fun invoke(): Flow<Address?> =
        repository.getSelectedAddress()
}