package com.gericass.githubclientmvrx.data.model

data class Token(
    val access_token: String,
    val scope: String,
    val token_type: String
)