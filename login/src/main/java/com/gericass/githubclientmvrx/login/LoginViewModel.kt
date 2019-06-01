package com.gericass.githubclientmvrx.login

import com.airbnb.mvrx.Async
import com.airbnb.mvrx.MvRxState
import com.airbnb.mvrx.MvRxViewModelFactory
import com.airbnb.mvrx.Uninitialized
import com.airbnb.mvrx.ViewModelContext
import com.gericass.githubclientmvrx.common.core.MvRxViewModel
import com.gericass.githubclientmvrx.data.AuthRepository
import com.gericass.githubclientmvrx.data.model.Token
import com.gericass.githubclientmvrx.login.constants.AuthInfo.CLIENT_ID
import com.gericass.githubclientmvrx.login.constants.AuthInfo.CLIENT_SECRET
import org.koin.android.ext.android.inject

data class LoginState(
        val token: String? = null,
        val intentRequest: Async<Token> = Uninitialized
) : MvRxState

class LoginViewModel(
        initialState: LoginState,
        private val authRepository: AuthRepository
) : MvRxViewModel<LoginState>(initialState) {


    fun getToken(code: String) {
        authRepository
                .getToken(CLIENT_ID, CLIENT_SECRET, code)
                .execute { copy(intentRequest = it, token = it()?.access_token) }
    }

    companion object : MvRxViewModelFactory<LoginViewModel, LoginState> {

        override fun create(viewModelContext: ViewModelContext, state: LoginState): LoginViewModel {
            val repository: AuthRepository by viewModelContext.activity.inject()
            return LoginViewModel(state, repository)
        }
    }
}