package com.example.getir.presention.authView

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.getir.domain.auth.AuthRepository
import com.example.getir.domain.auth.AuthResult
import com.example.getir.domain.auth.LoginRequest
import com.example.getir.domain.auth.LoginUseCase
import com.example.getir.domain.auth.RegisterRequest
import com.example.getir.domain.auth.RegisterUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class AuthViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val registerUseCase: RegisterUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(AuthState())
    val state: StateFlow<AuthState> = _state

    fun onEvent(event: AuthEvent) {
        when (event) {
            is AuthEvent.Login -> login(event.email, event.password)
            is AuthEvent.Register -> register(event.name, event.email, event.password)
            AuthEvent.ClearError -> _state.value = _state.value.copy(error = null)
        }
    }

    private fun login(email: String, password: String) {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true, error = null)

            when (val result = loginUseCase(email, password)) {
                is AuthResult.Success -> _state.value =
                    _state.value.copy(isLoading = false, isLoggedIn = true)

                is AuthResult.Error -> _state.value =
                    _state.value.copy(isLoading = false, error = result.message)
            }
        }
    }

    private fun register(name: String, email: String, password: String) {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true, error = null)

            when (val result = registerUseCase(name, email, password)) {
                is AuthResult.Success -> _state.value =
                    _state.value.copy(isLoading = false, isLoggedIn = true)

                is AuthResult.Error -> _state.value =
                    _state.value.copy(isLoading = false, error = result.message)
            }
        }
    }
}