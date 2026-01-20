package com.example.getir.domain.address

import com.example.getir.domain.address.AddressRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAddressesUseCase @Inject constructor(
    private val repository: AddressRepository
) {
    operator fun invoke(userId: String): Flow<List<Address>> =
        repository.getAddresses(userId)
}