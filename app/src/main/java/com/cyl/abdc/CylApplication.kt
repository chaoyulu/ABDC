package com.cyl.abdc

import android.app.Application
import com.cyl.utils.toast.ToastUtils

class CylApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        ToastUtils.init(this)
    }
}