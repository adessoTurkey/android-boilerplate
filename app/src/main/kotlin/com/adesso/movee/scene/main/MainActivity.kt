package com.adesso.movee.scene.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.core.net.toUri
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.adesso.movee.R
import com.adesso.movee.base.BaseBindingActivity
import com.adesso.movee.databinding.ActivityMainBinding
import com.adesso.movee.internal.extension.observeNonNull
import com.adesso.movee.internal.extension.showPopup
import com.adesso.movee.navigation.NavigationCommand
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseBindingActivity<MainViewModel, ActivityMainBinding>() {

    override val layoutId get() = R.layout.activity_main

    val navController: NavController by lazy { findNavController(R.id.main_host_fragment) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binder.viewModel = viewModel
        binder.lifecycleOwner = this

        observeNavigation()
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp()
    }

    private fun observeNavigation() {
        viewModel.navigation.observeNonNull(this) {
            it.getContentIfNotHandled()?.let { command ->
                handleNavigation(command)
            }
        }
    }

    private fun handleNavigation(command: NavigationCommand) {
        when (command) {
            is NavigationCommand.ToDirection -> navController.navigate(command.directions)
            is NavigationCommand.ToDeepLink -> navController.navigate(command.deepLink.toUri())
            is NavigationCommand.Popup -> showPopup(command.model, command.listener)
            is NavigationCommand.Back -> navController.navigateUp()
        }
    }

    companion object {
        fun getStartIntent(context: Context) = Intent(context, MainActivity::class.java)
    }
}
