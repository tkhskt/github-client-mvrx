package com.gericass.githubclientmvrx.data

import com.gericass.githubclientmvrx.data.model.Token
import io.reactivex.Completable
import io.reactivex.Observable

interface AuthRepository {

    fun getToken(clientId: String, clientSecret: String, code: String): Observable<Token>

    fun saveToken(token: String): Completable

    fun deleteToken(): Completable

    fun isLoggedIn(): Boolean
}