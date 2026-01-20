package com.example.getir.presention.map

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.getir.domain.address.Address
import com.example.getir.domain.address.AddressRepository
import com.example.getir.domain.address.DeleteAddressUseCase
import com.example.getir.domain.address.GetAddressByIdUseCase
import com.example.getir.domain.address.GetAddressesUseCase
import com.example.getir.domain.address.GetSelectedAddressUseCase
import com.example.getir.domain.address.SaveAddressUseCase
import com.example.getir.domain.address.UpdateAddressUseCase
import com.example.getir.domain.auth.AuthRepository
import com.example.getir.presention.map.AddressEvent
import com.example.getir.presention.map.AddressUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddressViewModel @Inject constructor(
    private val getAddresses: GetAddressesUseCase,
    private val getSelectedAddress: GetSelectedAddressUseCase,
    private val saveAddress: SaveAddressUseCase,
    private val deleteAddress: DeleteAddressUseCase,
    private val updateAddress: UpdateAddressUseCase,
    private val getAddressById: GetAddressByIdUseCase,
    private val authRepository: AuthRepository
) : ViewModel() {

    private val userId: String = authRepository.getUserId()
        ?: throw IllegalStateException("User not logged in")

    private val _uiState = MutableStateFlow(AddressUiState())
    val uiState: StateFlow<AddressUiState> = _uiState

    init {
        observeSelectedAddress()
    }

    private fun observeSelectedAddress() {
        viewModelScope.launch {
            getSelectedAddress().collectLatest { address ->
                _uiState.update { it.copy(selectedAddress = address) }
            }
        }
    }

    fun onEvent(event: AddressEvent) {
        when (event) {
            AddressEvent.LoadAddresses -> loadAddresses()
            is AddressEvent.SelectAddress -> selectAddress(event.address)
            is AddressEvent.SaveAddress -> save(event.address)
            is AddressEvent.DeleteAddress -> delete(event.addressId)
            is AddressEvent.UpdateAddress -> update(event.address)
            AddressEvent.ClearMessage -> clearMessages()
        }
    }

    private fun selectAddress(address: Address) {
        _uiState.update { it.copy(selectedAddress = address,
            isSaved = false,
            error = null) }
    }

    private fun clearMessages() {
        _uiState.update { it.copy(error = null, isSaved = false) }
    }

    private fun loadAddresses() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            getAddresses(userId).collectLatest { list ->
                _uiState.update {
                    it.copy(isLoading = false, addresses = list)
                }
            }
        }
    }

    private fun save(address: Address) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            try {
                saveAddress(address.copy(userId = userId))
                _uiState.update { it.copy(isLoading = false, isSaved = true) }
                loadAddresses()
            } catch (e: Exception) {
                _uiState.update { it.copy(isLoading = false, error = e.message) }
            }
        }
    }

    private fun delete(addressId: String) {
        viewModelScope.launch {
            try {
                deleteAddress(addressId)
                loadAddresses()
            } catch (e: Exception) {
                _uiState.update { it.copy(error = e.message) }
            }
        }
    }

    private fun update(address: Address) {
        viewModelScope.launch {
            try {
                updateAddress(address.copy(userId = userId))
                loadAddresses()
            } catch (e: Exception) {
                _uiState.update { it.copy(error = e.message) }
            }
        }
    }

    fun fetchAddressById(addressId: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            try {
                val address = getAddressById(addressId)
                _uiState.update { it.copy(isLoading = false, selectedAddress = address) }
            } catch (e: Exception) {
                _uiState.update { it.copy(isLoading = false, error = e.message) }
            }
        }
    }

    fun setLocation(lat: Double, lng: Double) {
        _uiState.update { it.copy(selectedLat = lat, selectedLng = lng) }
    }
}