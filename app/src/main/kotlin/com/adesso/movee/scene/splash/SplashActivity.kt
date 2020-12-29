package com.adesso.movee.scene.splash

import android.os.Bundle
import com.adesso.movee.base.BaseActivity
import com.adesso.movee.scene.main.MainActivity

class SplashActivity : BaseActivity<SplashViewModel>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        startMainActivity()
    }

    private fun startMainActivity() {
        startActivity(MainActivity.getStartIntent(this))
        finish()
    }
}
