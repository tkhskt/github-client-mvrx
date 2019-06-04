package com.gericass.githubclientmvrx.main

import androidx.navigation.NavController

interface MainNavigator {

    fun NavController.navigateToSearch(keyword: String)
}