package com.outrageouscat.shufflefriends

import android.app.Application
import com.outrageouscat.shufflefriends.di.dataModule
import com.outrageouscat.shufflefriends.di.domainModule
import com.outrageouscat.shufflefriends.di.presentationModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@MyApplication)
            modules(presentationModule, domainModule, dataModule)
        }
    }
}
