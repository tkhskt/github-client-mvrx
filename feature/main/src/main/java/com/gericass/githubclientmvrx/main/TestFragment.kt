package com.gericass.githubclientmvrx.main

import com.gericass.githubclientmvrx.common.core.BaseFragment
import com.gericass.githubclientmvrx.common.core.simpleController
import com.gericass.githubclientmvrx.main.view.basicRow


class TestFragment : BaseFragment() {


    override fun epoxyController() = simpleController {
        (0..20).forEach {
            basicRow {
                id(it.toString())
                title(it.toString())
                subtitle(it.toString())
            }
        }
    }

}
