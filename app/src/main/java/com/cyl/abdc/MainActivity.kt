package com.cyl.abdc

import android.view.Gravity
import com.cyl.abdc.databinding.ActivityMainBinding
import com.cyl.utils.log.LogUtils
import com.cyl.utils.toast.ToastUtils
import com.cyl.vb.BaseViewBindingActivity
import com.hjq.toast.Toaster

class MainActivity : BaseViewBindingActivity<ActivityMainBinding>() {
    var index = 0
    override fun initialize() {
        Toaster.init(this.application)
        binding.btnCoil.setOnClickListener {
            LogUtils.logE(msg = "Coil图片加载框架")
            ToastUtils.toast(R.string.app_name)
            ++index
        }
        binding.btnGlide.setOnClickListener {
            LogUtils.logW(msg = "Glide图片加载框架")
            ToastUtils.topToast(R.string.app_name)
            ++index
        }
        binding.btnViewvinding.setOnClickListener {
            LogUtils.logE(msg = "ViewBinding")
            ToastUtils.centerToast(R.string.app_name)
            ++index
        }
    }
}