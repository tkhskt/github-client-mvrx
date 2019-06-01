package com.gericass.githubclientmvrx.login

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.navArgs
import com.airbnb.mvrx.fragmentViewModel
import com.airbnb.mvrx.withState
import com.gericass.githubclientmvrx.common.core.BaseFragment
import com.gericass.githubclientmvrx.common.core.simpleController
import com.gericass.githubclientmvrx.login.constants.AuthInfo.AUTH_URL
import com.gericass.githubclientmvrx.main.MainActivity

class LoginFragment : BaseFragment() {

    private val viewModel: LoginViewModel by fragmentViewModel()

    private val args: LoginFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        if (viewModel.isLoggedIn()) {
            transitToMain()
            return
        }
        args.code?.let { code ->
            viewModel.getToken(code)
        } ?: run {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(AUTH_URL))
            startActivity(intent)
        }
    }

    private fun transitToMain() {
        val intent = MainActivity.createIntent(requireActivity())
        startActivity(intent)
        requireActivity().finish()
    }


    override fun invalidate() = withState(viewModel) { state ->
        state.token?.let {
            viewModel.saveToken(it)
            transitToMain()
        } ?: return@withState
    }

    override fun epoxyController() = simpleController(viewModel) { state ->
    }
}