package com.cyl.vb

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import java.lang.reflect.ParameterizedType

abstract class BaseViewBindingActivity<VB : ViewBinding> : AppCompatActivity() {
    private lateinit var _binding: VB
    protected val binding get() = _binding

    override fun onCreate(savedInstanceState: Bundle?) {
        _binding = createViewBinding()
        beforeOnCreate()
        super.onCreate(savedInstanceState)
        setContentView(_binding.root)
        initialize()
    }

    private fun createViewBinding(): VB {
        val type = javaClass.genericSuperclass
        val clazz = (type as ParameterizedType).actualTypeArguments[0] as Class<VB>
        val method = clazz.getMethod("inflate", LayoutInflater::class.java)
        return method.invoke(null, layoutInflater) as VB
    }

    protected abstract fun initialize()
    protected open fun beforeOnCreate() {}
}