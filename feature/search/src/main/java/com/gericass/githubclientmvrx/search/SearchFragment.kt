package com.gericass.githubclientmvrx.search


import com.airbnb.mvrx.fragmentViewModel
import com.gericass.githubclientmvrx.common.core.BaseFragment
import com.gericass.githubclientmvrx.common.core.simpleController

class SearchFragment : BaseFragment() {

    private val viewModel: SearchViewModel by fragmentViewModel()

    override fun epoxyController() = simpleController(viewModel) { }
}
