package com.gericass.githubclientmvrx.data

import com.gericass.githubclientmvrx.common.extensions.observeOnMainThread
import com.gericass.githubclientmvrx.data.model.Token
import com.gericass.githubclientmvrx.data.remote.GitHubClient
import io.reactivex.Observable
import retrofit2.Retrofit

class AuthRepositoryImpl(
    private val retrofit: Retrofit
) : AuthRepository {

    private val client by lazy { retrofit.create(GitHubClient::class.java) }


    override fun getToken(clientId: String, clientSecret: String, code: String): Observable<Token> {
        val request = mapOf(
            "client_id" to clientId,
            "client_secret" to clientSecret,
            "code" to code
        )
        return client.getToken("application/json", request)
            .observeOnMainThread()
    }
}