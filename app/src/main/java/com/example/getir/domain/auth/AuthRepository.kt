package com.example.getir.domain.auth


interface AuthRepository {

    suspend fun login(
        request: LoginRequest
    ): AuthResult

    suspend fun register(
        request: RegisterRequest
    ): AuthResult
}