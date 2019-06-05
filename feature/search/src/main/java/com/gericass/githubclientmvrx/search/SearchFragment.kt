package com.gericass.githubclientmvrx.search


import android.os.Bundle
import android.view.View
import com.airbnb.mvrx.fragmentViewModel
import com.gericass.githubclientmvrx.common.core.BaseFragment
import com.gericass.githubclientmvrx.common.core.simpleController
import com.gericass.githubclientmvrx.common.view.loadingRow
import com.gericass.githubclientmvrx.common.view.repositoryRow
import timber.log.Timber

class SearchFragment : BaseFragment() {

    private val viewModel: SearchViewModel by fragmentViewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.asyncSubscribe(SearchState::searchRequest, onFail = {
            Timber.e(it)
        })
    }

    override fun epoxyController() = simpleController(viewModel) { state ->
        state.searchItems.forEach {
            repositoryRow {
                id(it.id)
                it.full_name?.let { repoName(it) }
                it.description?.let { description(it) }
                it.stargazers_count?.toString()?.let { starCount(it) }
            }
        }
        loadingRow {
            id("loading${state.searchItems.size}")
            onBind { _, _, _ -> viewModel.search() }
        }
    }
}
