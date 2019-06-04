package com.gericass.githubclientmvrx.main.activity

import com.airbnb.mvrx.Async
import com.airbnb.mvrx.Loading
import com.airbnb.mvrx.MvRxState
import com.airbnb.mvrx.MvRxViewModelFactory
import com.airbnb.mvrx.Uninitialized
import com.airbnb.mvrx.ViewModelContext
import com.gericass.githubclientmvrx.common.core.MvRxViewModel
import com.gericass.githubclientmvrx.data.GitHubRepository
import com.gericass.githubclientmvrx.data.model.ReceiveEvent
import org.koin.android.ext.android.inject

data class ActivityState(
        val events: List<ReceiveEvent> = emptyList(),
        val eventRequest: Async<List<ReceiveEvent>> = Uninitialized
) : MvRxState


const val PER_PAGE = 50

class ActivityViewModel(
        initialState: ActivityState,
        private val gitHubRepository: GitHubRepository
) : MvRxViewModel<ActivityState>(initialState) {

    fun fetch(userName: String) = withState { state ->
        if (state.eventRequest is Loading) return@withState

        gitHubRepository.getReceiveEvents(userName, state.events.size / PER_PAGE + 1)
                .execute { copy(eventRequest = it, events = events + (it() ?: emptyList())) }
    }


    companion object : MvRxViewModelFactory<ActivityViewModel, ActivityState> {

        override fun create(viewModelContext: ViewModelContext, state: ActivityState): ActivityViewModel {
            val repository: GitHubRepository by viewModelContext.activity.inject()
            return ActivityViewModel(state, repository)
        }
    }
}