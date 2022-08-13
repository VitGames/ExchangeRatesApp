package com.vitgames.softcorptest

import android.app.Application
import com.vitgames.softcorptest.utils.di.DaggerMainAppComponent
import com.vitgames.softcorptest.utils.di.MainAppComponent
import com.vitgames.softcorptest.utils.di.MainAppModule

class MainApplication : Application() {

    lateinit var appComponent: MainAppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerMainAppComponent
            .builder()
            .mainAppModule(MainAppModule())
            .build()
    }
}