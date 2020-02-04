package com.saber.green.memomania.application

import android.app.Application
import android.content.Context
import androidx.multidex.MultiDex

class TilesApplication : Application() {
    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }
}
