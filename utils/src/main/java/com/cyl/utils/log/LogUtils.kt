package com.cyl.utils.log

import android.util.Log


private const val TAG = "DefaultTag"

object LogUtils {
    fun logI(tag: String = TAG, msg: String?) {
        segmentPrint(msg.toString()) {
            Log.i(tag, it)
        }
    }

    fun logE(tag: String = TAG, msg: String?) {
        segmentPrint(msg.toString()) {
            Log.e(tag, it)
        }
    }

    fun logD(tag: String = TAG, msg: String?) {
        segmentPrint(msg.toString()) {
            Log.d(tag, it)
        }
    }

    fun logW(tag: String = TAG, msg: String?) {
        segmentPrint(msg.toString()) {
            Log.w(tag, it)
        }
    }

    fun logV(tag: String = TAG, msg: String?) {
        segmentPrint(msg.toString()) {
            Log.v(tag, it)
        }
    }
}

private const val maxLength = 4000 // 一次最多打印4000字节
private fun segmentPrint(msg: String, realPrint: (String) -> Unit) {
    if (msg.length < 1000) {
        realPrint(msg)
        return
    }

    var bytes = msg.toByteArray()
    if (bytes.isEmpty()) return

    if (maxLength >= bytes.size) {
        realPrint(msg)
        return
    }

    while (maxLength < bytes.size) {
        val subString = splitString(bytes)
        realPrint(subString)
        bytes = bytes.copyOfRange(subString.toByteArray().size, bytes.size)
    }

    val str = String(bytes)
    realPrint(str)
}

private fun splitString(byteArray: ByteArray): String {
    if (byteArray.isEmpty()) return ""
    if (maxLength >= byteArray.size) return String(byteArray)

    var subBytes = byteArray.copyOf(maxLength)
    var i = subBytes.size - 1
    while (i > 0 && (subBytes[i].toInt() and 0xC0) == 0x80) {
        i--
    }
    if (i < subBytes.size - 1) {
        subBytes = subBytes.copyOf(i)
    }

    return String(subBytes)
}

fun String?.logI(tag: String = TAG) {
    segmentPrint(this.toString()) {
        Log.i(tag, it)
    }
}

fun String?.logE(tag: String = TAG) {
    segmentPrint(this.toString()) {
        Log.e(tag, it)
    }
}

fun String?.logD(tag: String = TAG) {
    segmentPrint(this.toString()) {
        Log.d(tag, it)
    }
}

fun String?.logW(tag: String = TAG) {
    segmentPrint(this.toString()) {
        Log.w(tag, it)
    }
}

fun String?.logV(tag: String = TAG) {
    segmentPrint(this.toString()) {
        Log.v(tag, it)
    }
}

