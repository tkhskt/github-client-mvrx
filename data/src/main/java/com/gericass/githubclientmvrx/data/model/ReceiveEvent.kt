package com.gericass.githubclientmvrx.data.model

data class ReceiveEvent(
    val actor: Actor,
    val created_at: String,
    val id: String,
    val org: Org,
    val payload: Payload,
    val `public`: Boolean,
    val repo: Repo,
    val type: String
) {
    data class Repo(
        val id: Int,
        val name: String,
        val url: String
    )

    data class Org(
        val avatar_url: String,
        val gravatar_id: String,
        val id: Int,
        val login: String,
        val url: String
    )

    data class Actor(
        val avatar_url: String,
        val display_login: String,
        val gravatar_id: String,
        val id: Int,
        val login: String,
        val url: String
    )

    data class Payload(
        val action: String
    )
}