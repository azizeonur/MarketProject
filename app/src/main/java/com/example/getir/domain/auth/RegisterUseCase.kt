package com.example.getir.domain.auth

import javax.inject.Inject

class RegisterUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(
        name: String,
        email: String,
        password: String
    ) = repository.register(
        RegisterRequest(
            name = name,
            email = email,
            password = password
        )
    )
}