package com.vitgames.softcorptest

import android.app.Application
import com.vitgames.softcorptest.main_activity.DaggerMainAppComponent
import com.vitgames.softcorptest.main_activity.MainAppComponent
import com.vitgames.softcorptest.main_activity.MainAppModule


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