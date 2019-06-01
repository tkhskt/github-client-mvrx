package com.gericass.githubclientmvrx.login

import com.airbnb.mvrx.*
import com.gericass.githubclientmvrx.common.core.MvRxViewModel
import com.gericass.githubclientmvrx.data.AuthRepository
import com.gericass.githubclientmvrx.data.model.Token
import com.gericass.githubclientmvrx.login.constants.AuthInfo.CLIENT_ID
import com.gericass.githubclientmvrx.login.constants.AuthInfo.CLIENT_SECRET
import org.koin.android.ext.android.inject

data class LoginState(
    val token: String? = null,
    val tokenRequest: Async<Token> = Uninitialized
) : MvRxState

class LoginViewModel(
    initialState: LoginState,
    private val authRepository: AuthRepository
) : MvRxViewModel<LoginState>(initialState) {

    fun isLoggedIn(): Boolean {
        return authRepository.isLoggedIn()
    }

    fun getToken(code: String) = withState { state ->
        if (state.tokenRequest is Loading) return@withState
        authRepository
            .getToken(CLIENT_ID, CLIENT_SECRET, code)
            .execute { copy(tokenRequest = it, token = it()?.access_token) }
    }

    fun saveToken(token: String) {
        authRepository.saveToken(token)
            .execute { copy() }
    }

    companion object : MvRxViewModelFactory<LoginViewModel, LoginState> {

        override fun create(viewModelContext: ViewModelContext, state: LoginState): LoginViewModel {
            val repository: AuthRepository by viewModelContext.activity.inject()
            return LoginViewModel(state, repository)
        }
    }
}