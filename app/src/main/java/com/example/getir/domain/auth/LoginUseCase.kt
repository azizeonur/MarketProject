package com.example.getir.domain.auth
import com.example.getir.domain.auth.LoginRequest
import com.example.getir.domain.auth.AuthRepository

import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val repository: AuthRepository,
) {
    suspend operator fun invoke(
        email: String,
        password: String
    ) = repository.login(
        LoginRequest(
            email = email,
            password = password
        )
    )
}