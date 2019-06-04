package com.gericass.githubclientmvrx.main.activity


import android.os.Bundle
import android.view.View
import com.airbnb.mvrx.Loading
import com.airbnb.mvrx.existingViewModel
import com.airbnb.mvrx.fragmentViewModel
import com.gericass.githubclientmvrx.common.core.BaseFragment
import com.gericass.githubclientmvrx.common.core.simpleController
import com.gericass.githubclientmvrx.main.MainState
import com.gericass.githubclientmvrx.main.MainViewModel
import com.gericass.githubclientmvrx.main.view.activityRow
import timber.log.Timber


class ActivityFragment : BaseFragment() {

    private val viewModel: ActivityViewModel by fragmentViewModel()

    private val mainViewModel: MainViewModel by existingViewModel()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.asyncSubscribe(ActivityState::eventRequest, onFail = {
            Timber.e(it)
        })
        mainViewModel.asyncSubscribe(MainState::userRequest, onSuccess = {
            it.login?.let { loginName ->
                viewModel.fetch(loginName)
            }
        })
    }

    override fun epoxyController() = simpleController(viewModel) { state ->
        if (state.eventRequest is Loading) return@simpleController
        state.events.forEach {
            activityRow {
                id(it.id)
                userName(it.actor.login)
                userImage(it.actor.avatar_url)
                repoName(it.repo.name)
                event(it)
                time(it.created_at)
            }
        }

    }
}
