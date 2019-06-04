package com.gericass.githubclientmvrx.search

import android.annotation.SuppressLint
import android.os.Parcelable
import com.airbnb.mvrx.*
import com.gericass.githubclientmvrx.common.core.MvRxViewModel
import com.gericass.githubclientmvrx.data.GitHubRepository
import com.gericass.githubclientmvrx.data.model.Search
import kotlinx.android.parcel.Parcelize
import org.koin.android.ext.android.inject

private const val PER_PAGE = 50

@SuppressLint("ParcelCreator")
@Parcelize
data class SearchArgs(val keyword: String) : Parcelable

data class SearchState(
    val keyword: String,
    val searchItems: List<Search.Item> = emptyList(),
    val searchRequest: Async<Search> = Uninitialized
) : MvRxState {
    constructor(args: SearchArgs) : this(keyword = args.keyword)
}

class SearchViewModel(
    initialState: SearchState,
    private val repository: GitHubRepository
) : MvRxViewModel<SearchState>(initialState) {

    fun search(keyword: String) = withState { state ->
        if (state.searchRequest is Loading) return@withState
        repository.search(keyword, state.searchItems.size / PER_PAGE + 1)
            .execute { copy(searchItems = searchItems + (it()?.items ?: emptyList())) }
    }

    companion object : MvRxViewModelFactory<SearchViewModel, SearchState> {

        override fun create(viewModelContext: ViewModelContext, state: SearchState): SearchViewModel {
            val repository: GitHubRepository by viewModelContext.activity.inject()
            return SearchViewModel(state, repository)
        }
    }
}