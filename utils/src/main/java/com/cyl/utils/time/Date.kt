package com.cyl.utils.time

import android.text.TextUtils
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by hcc on 2023/2/17 .
 */
/**
 * 根据毫秒时间戳获取对应格式的时间
 */
fun getTimeByMillis(millis: Long, format: String = "yyyy-MM-dd HH:mm:ss"): String? {
    try {
        val simpleFormat = SimpleDateFormat(format, Locale.getDefault())
        return simpleFormat.format(millis)
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return null
}

fun formatMillis(timeMillis: Long, showHour: Boolean = true): String {
    val time = timeMillis / 1000
    val hh = if (time / 3600 > 9) time / 3600 else "0" + time / 3600
    val mm = if (time % 3600 / 60 > 9) time % 3600 / 60 else "0" + time % 3600 / 60
    val ss = if (time % 3600 % 60 > 9) time % 3600 % 60 else "0" + time % 3600 % 60
    return if (!showHour) "$mm:$ss" else "$hh:$mm:$ss"
}

/**
 * 获取 月/日 格式的时间
 */
fun getDateMd(millis: Long) = getTimeByMillis(millis, "MM/dd")

/**
 * 获取 时:分 格式的时间
 */
fun getDateHm(millis: Long) = getTimeByMillis(millis, "HH:mm")

/**
 * 将时间戳转换成描述性时间（昨天、今天、明天，后天）
 */
fun descriptiveData(timestamp: Long): String? {
    var descriptiveText: String? = null
    val format = "MM/dd"
    //当前时间
    val currentTime = Calendar.getInstance()
    //要转换的时间
    val time = Calendar.getInstance()
    time.timeInMillis = timestamp
    //年相同
    if (currentTime[Calendar.YEAR] == time[Calendar.YEAR]) {
        //获取一年中的第几天并相减，取差值
        descriptiveText = when (currentTime[Calendar.DAY_OF_YEAR] - time[Calendar.DAY_OF_YEAR]) {
            2 -> {
                "前天"
            }

            1 -> {
                "昨天"
            }

            0 -> {
                "今天"
            }

            -1 -> {
                "明天"
            }

            -2 -> {
                "后天"
            }

            else -> {
                null
            }
        }
    }
    val simpleDateFormat = SimpleDateFormat(format, Locale.getDefault())
    val formatDate = simpleDateFormat.format(time.time)
    return if (!TextUtils.isEmpty(descriptiveText)) {
        "$formatDate $descriptiveText"
    } else formatDate
}

/**
 * 是否是今天
 */
fun isToday(timestamp: Long): Boolean {
    //当前时间
    val currentTime = Calendar.getInstance()
    //要转换的时间
    val time = Calendar.getInstance()
    time.timeInMillis = timestamp
    return if (currentTime[Calendar.YEAR] == time[Calendar.YEAR]) {
        //获取一年中的第几天并相减，取差值
        currentTime[Calendar.DAY_OF_YEAR] - time[Calendar.DAY_OF_YEAR] == 0
    } else {
        false
    }
}

/**
 * 获取是周几
 */
fun getWeekday(millis: Long): String {
    //必须yyyy-MM-dd
    val sd = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    val sdw = SimpleDateFormat("E", Locale.getDefault())
    var d: Date? = null
    try {
        val time = getTimeByMillis(millis, "yyyy-MM-dd")
        time?.let {
            d = sd.parse(time)
        }
    } catch (e: java.text.ParseException) {
        e.printStackTrace()
    }
    return if (d == null) "" else sdw.format(d!!)
}

/**
 * 获取当前时刻的下一个整点的毫秒时间戳
 */
fun getNextHourMillis(): Long {
    val currentMillis = System.currentTimeMillis()
    val time = getTimeByMillis(currentMillis)
    if (time != null && time.length == 19) {
        val nextHour = time.substring(11, 13).toInt() + 1
        val date = time.substring(0, 10)
        val fullTime = "$date $nextHour:00:00"
        return getTimeStamp(fullTime)
    }
    return System.currentTimeMillis()
}

/**
 * 把时间转化成时间戳
 *
 * @param time 2015-06-28 00:00:00
 * @return 时间戳ms
 * @ps 换算成秒需要/1000
 */
fun getTimeStamp(time: String?): Long {
    if (time.isNullOrEmpty()) {
        return -1L
    }
    var date: Date? = null
    try {
        val format = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        date = format.parse(time)
    } catch (e: java.lang.Exception) {
        e.printStackTrace()
    }
    return date!!.time
}