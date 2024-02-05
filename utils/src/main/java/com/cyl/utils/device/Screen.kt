package com.cyl.utils.device

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.os.Build
import android.view.View
import android.view.ViewGroup.MarginLayoutParams
import android.view.ViewTreeObserver
import android.view.WindowManager
import android.widget.FrameLayout
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.core.view.updateLayoutParams

/**
 * 获取屏幕宽度
 */
fun Context.screenWidth(): Int {
    val wm = getSystemService(Context.WINDOW_SERVICE) as WindowManager
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        wm.currentWindowMetrics.bounds.width()
    } else {
        wm.defaultDisplay.width
    }
}

/**
 * 获取屏幕高度
 */
fun Context.screenHeight(): Int {
    val wm = getSystemService(Context.WINDOW_SERVICE) as WindowManager
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        wm.currentWindowMetrics.bounds.height()
    } else {
        wm.defaultDisplay.height
    }
}

/**
 * 状态栏高度
 */
fun Context.statusBarHeight(): Int {
    val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")
    return resources.getDimensionPixelSize(resourceId)
}

/**
 * 导航栏高度
 */
fun Activity.navigationBarHeight(): Int {
    val resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android")
    return resources.getDimensionPixelSize(resourceId)
}

/**
 * 沉浸式系统栏
 */
fun Activity.immersionBar(
    type: ImmersionType = ImmersionType.BOTH,
    barColor: Int = Color.TRANSPARENT,
    fontDark: Boolean = true,
    needPadding: Boolean = true // 沉浸式后，是否需要设置padding避开状态栏和导航栏等位置
) {
    WindowCompat.setDecorFitsSystemWindows(window, false)

    val (controller, contentView) = getInsetsController()
    var paddingTop = 0
    var paddingBottom = 0

    if (type == ImmersionType.STATUS_BAR || type == ImmersionType.BOTH) {
        window.statusBarColor = barColor
        controller.isAppearanceLightStatusBars = fontDark
        paddingTop = statusBarHeight()
    }

    if (type == ImmersionType.NAVIGATION_BAR || type == ImmersionType.BOTH) {
        window.navigationBarColor = barColor
        controller.isAppearanceLightNavigationBars = fontDark
        paddingBottom = navigationBarHeight()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            window.navigationBarDividerColor = barColor
        }
    }

    if (needPadding) {
        contentView.viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                contentView.setPadding(0, paddingTop, 0, paddingBottom)
                contentView.viewTreeObserver.removeOnGlobalLayoutListener(this)
            }
        })
    }
}

/**
 * 设置全屏
 */
fun Activity.fullScreen() {
    immersionBar(needPadding = false)
}

private fun Activity.getInsetsController(contentView: View = findViewById<FrameLayout>(android.R.id.content)): Pair<WindowInsetsControllerCompat, View> {
    val controller = WindowCompat.getInsetsController(window, contentView)
    return Pair(controller, contentView)
}

/**
 * 切换系统栏的可见性
 * 导航栏可正常隐藏，但是隐藏状态栏时，状态栏的文字会隐藏，但是整个状态栏都是黑的
 */
fun Activity.toggleSystemBarVisibility(
    show: Boolean,
    type: ImmersionType = ImmersionType.BOTH,
    behavior: Int = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
) {
    val (controller, _) = getInsetsController(window.decorView)
    val bar = when (type) {
        ImmersionType.STATUS_BAR -> WindowInsetsCompat.Type.statusBars()
        ImmersionType.NAVIGATION_BAR -> WindowInsetsCompat.Type.navigationBars()
        ImmersionType.BOTH -> WindowInsetsCompat.Type.systemBars()
    }
    if (show) {
        controller.show(bar)
    } else {
        controller.hide(bar)
    }
    controller.systemBarsBehavior = behavior
}

/**
 * 避免view被系统拦挡住
 * @param topNeeded 上方内容是否要在状态栏之下
 * @param bottomNeeded 下方内容是否要在导航栏之上
 */
fun avoidViewInDangerArea(view: View, topNeeded: Boolean = true, bottomNeeded: Boolean = true) {
    ViewCompat.setOnApplyWindowInsetsListener(view) { v, windowInsets ->
        val insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars())
        v.updateLayoutParams<MarginLayoutParams> {
            leftMargin = insets.left
            if (bottomNeeded) bottomMargin = insets.bottom
            rightMargin = insets.right
            if (topNeeded) topMargin = insets.top
        }
        WindowInsetsCompat.CONSUMED
    }
}

fun Context.dp2px(dpValue: Float): Int {
    val scale = resources.displayMetrics.density
    return (dpValue * scale + 0.5f).toInt()
}

fun Context.px2dp(pxValue: Float): Int {
    val scale = resources.displayMetrics.density
    return (pxValue / scale + 0.5f).toInt()
}