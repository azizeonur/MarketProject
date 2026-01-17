package com.example.getir.data.auth

import com.example.getir.data.product.ProductApi
import com.example.getir.domain.auth.AuthRepository
import com.example.getir.domain.auth.AuthResult
import com.example.getir.domain.auth.AuthUser
import com.example.getir.domain.auth.LoginRequest
import com.example.getir.domain.auth.RegisterRequest
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val api: ProductApi
) : AuthRepository {

    override suspend fun login(request: LoginRequest): AuthResult {
        return try {
            val response = api.login(
                LoginRequestDto(
                    email = request.email,
                    password = request.password
                )
            )
            AuthResult.Success(AuthUser(token = response.token))
        } catch (e: Exception) {
            AuthResult.Error(e.message ?: "Login failed")
        }
    }

    override suspend fun register(request: RegisterRequest): AuthResult {
        return try {
            val response = api.register(
                RegisterRequestDto(
                    name = request.name,
                    email = request.email,
                    password = request.password
                )
            )
            AuthResult.Success(AuthUser(token = response.token))
        } catch (e: Exception) {
            AuthResult.Error(e.message ?: "Register failed")
        }
    }
}