package com.gericass.githubclientmvrx.core

import com.airbnb.epoxy.AsyncEpoxyController
import com.airbnb.epoxy.EpoxyController

class EpoxyController(
        val buildModelsCallback: EpoxyController.() -> Unit = {}
) : AsyncEpoxyController() {
    override fun buildModels() {
        buildModelsCallback()
    }
}