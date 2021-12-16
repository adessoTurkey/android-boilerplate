package com.adesso.movee.scene.splash

import android.app.Application
import com.adesso.movee.base.BaseAndroidViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(application: Application) :
    BaseAndroidViewModel(application)
