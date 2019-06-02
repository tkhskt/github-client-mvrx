package com.gericass.githubclientmvrx.data.remote

import com.gericass.githubclientmvrx.data.model.Event
import com.gericass.githubclientmvrx.data.model.LoginUser
import com.gericass.githubclientmvrx.data.model.ReceiveEvent
import com.gericass.githubclientmvrx.data.model.Token
import io.reactivex.Observable
import retrofit2.http.*

interface GitHubClient {
    @POST(URL_GET_TOKEN)
    @FormUrlEncoded
    fun getToken(@Header(ACCEPT_HEADER) header: String, @FieldMap body: Map<String, String>): Observable<Token>

    @GET(GET_EVENTS)
    fun getEvents(@Header(TOKEN_HEADER) header: String): Observable<List<Event>>

    @GET(GET_RECEIVE_EVENT)
    fun getReceiveEvents(@Header(TOKEN_HEADER) header: String, @Path("user_name") userName: String): Observable<List<ReceiveEvent>>

    @GET(GET_LOGIN_USER)
    fun getLoginUser(@Header(TOKEN_HEADER) header: String): Observable<LoginUser>

}