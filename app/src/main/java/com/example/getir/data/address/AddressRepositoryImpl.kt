package com.example.getir.data.address

import com.example.getir.data.product.ProductApi
import com.example.getir.domain.address.Address
import com.example.getir.domain.address.AddressRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject


class AddressRepositoryImpl @Inject constructor(
    private val api: ProductApi
) : AddressRepository {

    // Seçili adresi tutacak StateFlow
    private val _selectedAddress = MutableStateFlow<Address?>(null)
    override fun getSelectedAddress(): Flow<Address?> = _selectedAddress

    fun selectAddress(address: Address) {
        _selectedAddress.value = address
    }

    override suspend fun saveAddress(address: Address) {
        api.saveAddress(address.toDto())
        // kaydedilen adresi seçili yap
        _selectedAddress.value = address
    }

    override fun getAddresses(userId: String): Flow<List<Address>> =
        // artık Flow dönüyoruz
        kotlinx.coroutines.flow.flow {
            val list = api.getAddresses(userId).map { it.toDomain() }
            emit(list)
        }

    override suspend fun deleteAddress(addressId: String) {
        api.deleteAddress(addressId)
        // silinen adres seçili ise temizle
        if (_selectedAddress.value?.id == addressId) {
            _selectedAddress.value = null
        }
    }

    override suspend fun updateAddress(address: Address) {
        api.updateAddress(address.toDto())
        // güncellenen adres seçili ise güncelle
        if (_selectedAddress.value?.id == address.id) {
            _selectedAddress.value = address
        }
    }

    override suspend fun getAddressById(addressId: String): Address {
        return api.getAddressById(addressId).toDomain()
    }
}