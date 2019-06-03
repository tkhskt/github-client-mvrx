package com.gericass.githubclientmvrx.data

import com.gericass.githubclientmvrx.data.model.Event
import com.gericass.githubclientmvrx.data.model.LoginUser
import com.gericass.githubclientmvrx.data.model.ReceiveEvent
import io.reactivex.Observable

interface GitHubRepository {

    fun getEvents(): Observable<List<Event>>

    fun getReceiveEvents(userName: String): Observable<List<ReceiveEvent>>

    fun getLoginUser(): Observable<LoginUser>
}