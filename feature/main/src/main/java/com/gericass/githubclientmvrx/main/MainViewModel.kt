package com.gericass.githubclientmvrx.main

import com.airbnb.mvrx.*
import com.gericass.githubclientmvrx.common.core.MvRxViewModel
import com.gericass.githubclientmvrx.data.GitHubRepository
import com.gericass.githubclientmvrx.data.model.LoginUser
import org.koin.android.ext.android.inject

data class MainState(
    val user: LoginUser? = null,
    val userRequest: Async<LoginUser> = Uninitialized
) : MvRxState

class MainViewModel(
    initialState: MainState,
    private val gitHubRepository: GitHubRepository
) : MvRxViewModel<MainState>(initialState) {

    init {
        fetch()
    }

    private fun fetch() {
        gitHubRepository.getLoginUser()
            .execute { copy(user = it(), userRequest = it) }
    }

    companion object : MvRxViewModelFactory<MainViewModel, MainState> {

        override fun create(viewModelContext: ViewModelContext, state: MainState): MainViewModel {
            val repository: GitHubRepository by viewModelContext.activity.inject()
            return MainViewModel(state, repository)
        }
    }
}