package com.cyl.utils.color

import android.app.Activity
import android.app.Dialog
import android.view.View
import android.widget.PopupWindow
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment

fun View.color(color: Int): Int = ContextCompat.getColor(context, color)

fun Dialog.color(color: Int) = ContextCompat.getColor(context, color)

fun Activity.color(color: Int) = ContextCompat.getColor(this, color)
fun PopupWindow.color(color: Int) = ContextCompat.getColor(contentView.context, color)

fun Fragment.color(color: Int) = ContextCompat.getColor(requireContext(), color)
fun android.app.Fragment.color(color: Int) = ContextCompat.getColor(context, color)