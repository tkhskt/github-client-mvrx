package com.gericass.githubclientmvrx.login

import android.content.Intent
import android.os.Bundle
import androidx.navigation.findNavController
import com.airbnb.mvrx.BaseMvRxActivity

class LoginActivity : BaseMvRxActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        intent?.data?.getQueryParameter("code")?.let {
            val action = LoginFragmentDirections.refresh(it)
            findNavController(R.id.nav_host).navigate(action)
        }
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
    }
}
