package com.cyl.vb

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import androidx.viewbinding.ViewBinding
import java.lang.reflect.ParameterizedType

/**
 * Created by hcc on 2021/10/29 .
 */
//abstract class BaseViewBindingDialog<VB : ViewBinding>(context: Context, themeRes: Int = 0) : Dialog(context, themeRes) {
//    private var _binding: VB? = null
//    protected val binding get() = _binding!!
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        _binding = createViewBinding()
//        setContentView(binding.root)
//        initDialogStyle()
//        initialize()
//    }
//
//    private fun initDialogStyle() {
//        window?.run {
//            setGravity(gravity())
//            // 底部弹出的Dialog
//            if (gravity() == Gravity.BOTTOM) {
//                setWindowAnimations(getWindowAnimations())
//                val padding = borderPadding()
//                decorView.setPadding(padding.left, padding.top, padding.right, padding.bottom)
//            }
//
//            setDimAmount(dimAmount())
//            // 不是底部弹出的Dialog才设置圆角
//            if (gravity() != Gravity.BOTTOM) binding.root.setCircularCorner(color(getBackgroundColor()), getCornerRadius())
//            setLayout(dialogWidth(), dialogHeight())
//            setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
//        }
//    }
//
//    open fun getWindowAnimations() = R.style.DialogBottom
//
//    private fun createViewBinding(): VB {
//        val type = javaClass.genericSuperclass
//        val clazz = (type as ParameterizedType).actualTypeArguments[0] as Class<VB>
//        val method = clazz.getMethod("inflate", LayoutInflater::class.java)
//        return method.invoke(null, layoutInflater) as VB
//    }
//
//    abstract fun initialize()
//
//    open fun getBackgroundColor() = R.color.white
//
//    open fun getCornerRadius() = 10f.dp2px(context)
//
//    open fun gravity() = Gravity.CENTER
//
//    open fun dimAmount() = window?.attributes?.dimAmount ?: 0.7f
//
//    open fun dialogWidth() = ScreenUtils.getScreenWidth() / 5 * 4
//
//    open fun dialogHeight() = ViewGroup.LayoutParams.WRAP_CONTENT
//
//    open fun borderPadding() = Padding(0, 0, 0, 0)
//
//    class Padding(val left: Int, val top: Int, val right: Int, val bottom: Int)
//}