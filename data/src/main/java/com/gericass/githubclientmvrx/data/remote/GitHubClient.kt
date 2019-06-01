package com.gericass.githubclientmvrx.data.remote

import com.gericass.githubclientmvrx.data.model.Token
import io.reactivex.Observable
import retrofit2.http.FieldMap
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Header
import retrofit2.http.POST

interface GitHubClient {
    @POST(URL_GET_TOKEN)
    @FormUrlEncoded
    fun getToken(@Header(ACCEPT_HEADER) header: String, @FieldMap body: Map<String, String>): Observable<Token>
}