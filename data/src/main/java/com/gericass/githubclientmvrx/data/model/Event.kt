package com.gericass.githubclientmvrx.data.model

import com.squareup.moshi.Json

data class Event(
        val actor: Actor,
        val created_at: String,
        val id: String,
        val org: Org,
        @Json(name = "public")
        val isPublic: Boolean,
        val repo: Repo,
        val type: String
) {
    data class Repo(
            val id: Int,
            val name: String,
            val url: String
    )

    data class Actor(
            val avatar_url: String,
            val gravatar_id: String,
            val id: Int,
            val login: String,
            val url: String
    )

    data class Org(
            val avatar_url: String,
            val gravatar_id: String,
            val id: Int,
            val login: String,
            val url: String
    )
}
