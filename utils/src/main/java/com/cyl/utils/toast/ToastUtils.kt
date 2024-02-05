package com.cyl.utils.toast

import android.app.Application
import android.view.Gravity
import android.widget.Toast
import com.cyl.utils.log.LogUtils
import com.hjq.toast.Toaster

object ToastUtils {

    private var application: Application? = null

    fun init(application: Application) {
        this.application = application
        Toaster.init(application)
    }

    /**
     * 正常普通的toast
     */
    fun toast(content: Int, duration: Int = Toast.LENGTH_SHORT) {
        customToast(content, duration)
    }

    fun toast(content: String, duration: Int = Toast.LENGTH_SHORT) {
        customToast(content, duration)
    }

    /**
     * 屏幕中间弹出的toast
     */
    fun centerToast(content: Int, duration: Int = Toast.LENGTH_SHORT) {
        customToast(content, duration, Gravity.CENTER)
    }

    fun centerToast(content: String, duration: Int = Toast.LENGTH_SHORT) {
        customToast(content, duration, Gravity.CENTER)
    }

    /**
     * 屏幕顶部弹出的toast
     */
    fun topToast(content: Int, duration: Int = Toast.LENGTH_SHORT) {
        customToast(content, duration, Gravity.TOP)
    }

    fun topToast(content: String, duration: Int = Toast.LENGTH_SHORT) {
        customToast(content, duration, Gravity.TOP)
    }

    fun customToast(content: Int, duration: Int = Toast.LENGTH_SHORT, gravity: Int = Gravity.BOTTOM) {
        if (application == null) return
        LogUtils.logE("Application : ", "$application")
        customToast(application!!.resources.getString(content), duration, gravity)
    }

    fun customToast(
        content: String,
        duration: Int = Toast.LENGTH_SHORT,
        gravity: Int = Gravity.BOTTOM
    ) {
        Toaster.setGravity(gravity)
        when (duration) {
            Toast.LENGTH_SHORT -> {
                Toaster.showShort(content)
            }

            Toast.LENGTH_LONG -> {
                Toaster.showLong(content)
            }

            else -> {
                Toaster.show(content)
            }
        }
    }
}