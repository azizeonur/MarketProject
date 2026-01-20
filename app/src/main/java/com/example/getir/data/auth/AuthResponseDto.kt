package com.example.getir.data.auth

import com.example.getir.domain.auth.AuthUser

data class AuthResponseDto(
    val token: String,
    val userId: String,

    )
fun AuthResponseDto.toDomain(): AuthUser {
    return AuthUser(
        userId = userId,
        token = token
    )
}