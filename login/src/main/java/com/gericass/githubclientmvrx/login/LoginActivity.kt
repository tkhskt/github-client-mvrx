package com.gericass.githubclientmvrx.login

import android.os.Bundle
import com.airbnb.mvrx.BaseMvRxActivity

class LoginActivity : BaseMvRxActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
    }
}
