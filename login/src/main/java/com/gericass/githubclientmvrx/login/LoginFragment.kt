package com.gericass.githubclientmvrx.login

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.navArgs
import com.airbnb.mvrx.fragmentViewModel
import com.gericass.githubclientmvrx.common.core.BaseFragment
import com.gericass.githubclientmvrx.common.core.simpleController
import com.gericass.githubclientmvrx.login.constants.AuthInfo.AUTH_URL

class LoginFragment : BaseFragment() {

    private val viewModel: LoginViewModel by fragmentViewModel()

    private val args: LoginFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val uri = AUTH_URL
        args.code?.let { code ->
            viewModel.getToken(code)
        } ?: run {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(uri))
            startActivity(intent)
        }
    }

    override fun epoxyController() = simpleController(viewModel) { state ->
        state.token?.let {

        }
    }
}