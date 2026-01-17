package com.example.getir.domain.auth

data class RegisterRequest(
    val name: String,
    val email: String,
    val password: String
)