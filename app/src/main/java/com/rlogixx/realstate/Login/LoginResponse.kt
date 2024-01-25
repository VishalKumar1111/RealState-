package com.rlogixx.realstate.Login

data class LoginResponse(
    val id: Int,
    val email: String,
    val access_token: String,
    val status: Int
)
