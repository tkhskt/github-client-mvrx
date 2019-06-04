package com.gericass.githubclientmvrx.data

import com.gericass.githubclientmvrx.data.model.Event
import com.gericass.githubclientmvrx.data.model.LoginUser
import com.gericass.githubclientmvrx.data.model.ReceiveEvent
import com.gericass.githubclientmvrx.data.model.Search
import io.reactivex.Observable

interface GitHubRepository {

    fun getEvents(): Observable<List<Event>>

    fun getReceiveEvents(userName: String, page: Int): Observable<List<ReceiveEvent>>

    fun getLoginUser(): Observable<LoginUser>

    fun search(keyword: String, page: Int): Observable<Search>
}