package com.gericass.githubclientmvrx.navigator

import androidx.navigation.NavController
import com.gericass.githubclientmvrx.main.MainFragmentDirections
import com.gericass.githubclientmvrx.main.MainNavigator

class MainNavigatorImpl : MainNavigator {
    override fun NavController.navigateToSearch(keyword: String) {
        val action = MainFragmentDirections.mainToSearch(keyword)
        navigate(action)
    }
}