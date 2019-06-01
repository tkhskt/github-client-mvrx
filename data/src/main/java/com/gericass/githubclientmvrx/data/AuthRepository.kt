package com.gericass.githubclientmvrx.data

import com.gericass.githubclientmvrx.data.model.Token
import io.reactivex.Observable

interface AuthRepository {

    fun getToken(clientId: String, clientSecret: String, code: String): Observable<Token>
}