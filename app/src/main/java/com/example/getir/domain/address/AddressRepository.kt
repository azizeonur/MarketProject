package com.example.getir.domain.address

import kotlinx.coroutines.flow.Flow

interface AddressRepository {
    suspend fun saveAddress(address: Address)
    fun getSelectedAddress(): Flow<Address?>
    fun getAddresses(userId: String): Flow<List<Address>>
    suspend fun deleteAddress(addressId: String)
    suspend fun getAddressById(addressId: String): Address
    suspend fun updateAddress(address: Address)
}