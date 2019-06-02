package com.gericass.githubclientmvrx.main.activity

import com.airbnb.mvrx.*
import com.gericass.githubclientmvrx.common.core.MvRxViewModel
import com.gericass.githubclientmvrx.data.GitHubRepository
import com.gericass.githubclientmvrx.data.model.ReceiveEvent
import org.koin.android.ext.android.inject

data class ActivityState(
    val events: List<ReceiveEvent> = emptyList(),
    val eventRequest: Async<List<ReceiveEvent>> = Uninitialized
) : MvRxState

class ActivityViewModel(
    initialState: ActivityState,
    private val gitHubRepository: GitHubRepository
) : MvRxViewModel<ActivityState>(initialState) {

    fun fetch(userName: String) {
        gitHubRepository.getReceiveEvents(userName)
            .execute { copy(eventRequest = it, events = it() ?: emptyList()) }
    }

    companion object : MvRxViewModelFactory<ActivityViewModel, ActivityState> {

        override fun create(viewModelContext: ViewModelContext, state: ActivityState): ActivityViewModel {
            val repository: GitHubRepository by viewModelContext.activity.inject()
            return ActivityViewModel(state, repository)
        }
    }
}