package com.cyl.utils.string

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonParser
import com.google.gson.JsonSyntaxException
import com.google.gson.reflect.TypeToken

/**
 * Created by hcc on 2023/2/17 .
 */
fun String?.toIntSafe(): Int {
    return try {
        if (this == null) 0 else Integer.parseInt(this)
    } catch (e: Exception) {
        0
    }
}

fun String?.isNotNullOrEmpty(): Boolean = this != null && this != "" && isNotEmpty()

fun String?.prettyJson(gson: Gson = GsonBuilder().setPrettyPrinting().create()): String {
    if (this.isNullOrEmpty() || !isJson()) return ""
    return gson.toJson(JsonParser.parseString(this))
}

/**
 * 将实体类转成json字符串后格式化
 */
fun <T> T.prettyJson(gson: Gson = GsonBuilder().setPrettyPrinting().create()): String {
    try {
        return gson.toJson(JsonParser.parseString(gson.toJson(this)))
    } catch (ignored: JsonSyntaxException) {
    }
    return ""
}

fun isEmpty(text: String?): Boolean {
    return text.isNullOrEmpty()
}

fun String?.isJson(): Boolean {
    var isJson = false
    try {
        if (!isEmpty(this)) {
            Gson().fromJson(this, Any::class.java)
            isJson = true
        }
    } catch (ignored: java.lang.Exception) {
    }
    return isJson
}

fun Context.copyText(text: String) {
    val manager = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    manager.setPrimaryClip(ClipData.newPlainText("newPlainTextLabel", text))
}

inline fun <reified T> parseJson(json: String): T {
    val type = object : TypeToken<T>() {}.type
    return Gson().fromJson(json, type)
}

// 是否是手机号码格式
fun String.isMatchPhoneFormat() = this.matches("^[1][3-9]\\d{9}$".toRegex())