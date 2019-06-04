package com.gericass.githubclientmvrx.data.remote

import com.gericass.githubclientmvrx.data.model.Event
import com.gericass.githubclientmvrx.data.model.LoginUser
import com.gericass.githubclientmvrx.data.model.ReceiveEvent
import com.gericass.githubclientmvrx.data.model.Search
import com.gericass.githubclientmvrx.data.model.Token
import io.reactivex.Observable
import retrofit2.http.FieldMap
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface GitHubClient {
    @POST(URL_GET_TOKEN)
    @FormUrlEncoded
    fun getToken(@Header(ACCEPT_HEADER) header: String, @FieldMap body: Map<String, String>): Observable<Token>

    @GET(GET_EVENTS)
    fun getEvents(@Header(TOKEN_HEADER) header: String): Observable<List<Event>>

    @GET(GET_RECEIVE_EVENT)
    fun getReceiveEvents(
            @Header(TOKEN_HEADER) header: String,
            @Path("user_name") userName: String,
            @Query("page") page: Int
    ): Observable<List<ReceiveEvent>>

    @GET(GET_LOGIN_USER)
    fun getLoginUser(@Header(TOKEN_HEADER) header: String): Observable<LoginUser>

    @GET(GET_SEARCH_REPOS)
    fun search(
            @Header(TOKEN_HEADER) header: String,
            @Query("q") keyword: String,
            @Query("page") page: Int
    ): Observable<Search>

}