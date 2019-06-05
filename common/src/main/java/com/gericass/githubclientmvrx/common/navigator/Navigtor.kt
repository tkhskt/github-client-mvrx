package com.gericass.githubclientmvrx.common.navigator

import androidx.navigation.NavController

interface Navigtor {
    interface MainNavigator {

        fun NavController.navigateToSearch(keyword: String)
    }
}