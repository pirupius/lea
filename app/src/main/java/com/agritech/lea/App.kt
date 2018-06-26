package com.agritech.lea

import android.app.Application
import com.agritech.lea.utils.VolleyService

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        VolleyService.initialize(this)
    }
}