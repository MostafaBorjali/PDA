package com.okala.test.core

import androidx.appcompat.app.AppCompatDelegate
import androidx.multidex.BuildConfig
import androidx.multidex.MultiDexApplication
import com.android.networkapi.core.Server
import com.android.networkapi.core.UrlType
import com.facebook.stetho.Stetho

class App : MultiDexApplication() {
    override fun onCreate() {
        super.onCreate()
        ObjectBox.build(this)
        Server.setServerType(UrlType.Public)
        if (BuildConfig.DEBUG){
            Stetho.initializeWithDefaults(this)
        }

        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
    }
}
