package com.doce.cactus.saba.findnear

import android.app.Application
import android.content.Context
import com.doce.cactus.saba.findnear.koin.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class FNApplication: Application() {

    init {
        instance = this
    }

    companion object {
        var instance: FNApplication? = null
        private fun applicationContext(): Context {
            return instance!!.applicationContext
        }
    }

    override fun onCreate() {
        super.onCreate()

        startKoin{
            androidLogger()
            androidContext(this@FNApplication)
            modules(listOf(
                viewModelModule
            ))
        }
    }

}