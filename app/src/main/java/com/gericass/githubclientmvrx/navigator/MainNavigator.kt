package com.gericass.githubclientmvrx.navigator

import android.os.Bundle
import androidx.navigation.NavController
import com.airbnb.mvrx.MvRx
import com.gericass.githubclientmvrx.main.MainFragmentDirections
import com.gericass.githubclientmvrx.main.MainNavigator
import com.gericass.githubclientmvrx.search.SearchArgs

class MainNavigatorImpl : MainNavigator {

    override fun NavController.navigateToSearch(keyword: String) {
        val p = SearchArgs(keyword)
        val bundle = Bundle().apply { putParcelable(MvRx.KEY_ARG, p) }
        val action = MainFragmentDirections.mainToSearch(keyword).actionId
        navigate(action, bundle)
    }
}