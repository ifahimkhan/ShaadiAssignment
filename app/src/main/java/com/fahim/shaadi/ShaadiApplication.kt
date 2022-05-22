package com.fahim.shaadi

import android.app.Application
import com.fahim.shaadi.dependencyInjection.dependencyInjector
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class ShaadiApplication : Application() {
    lateinit var dependencyInjector: dependencyInjector

    override fun onCreate() {
        super.onCreate()
        initDependencyInjector()
    }

    private fun initDependencyInjector() {
        dependencyInjector = dependencyInjector(this)
    }

}