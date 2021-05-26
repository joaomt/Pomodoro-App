package com.superplayer.pomodoro.common.extensions

import android.text.format.DateUtils
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

private val TIME_FORMAT = "%02d:%02d"
private val DF_DATE_DAY_MONTH = SimpleDateFormat("dd/MM")
private val DF_FULL_DATE = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")

fun Long.formatTime(): String = String.format(
    TIME_FORMAT,
    TimeUnit.MILLISECONDS.toMinutes(this),
    TimeUnit.MILLISECONDS.toSeconds(this) % 60
)

fun Long.formatToSeconds() : Int = TimeUnit.MILLISECONDS.toSeconds(this).toInt()

fun Date.getDayMonthFormated(): String? {
    return try {
        DF_DATE_DAY_MONTH.format(time)
    } catch (ex: Exception) {
        ex.printStackTrace()
        null
    }
}

fun Date.getTimeRelative():String? {
    DF_FULL_DATE.timeZone = TimeZone.getTimeZone("GMT")
    try {
        val now = System.currentTimeMillis()
        val ago = DateUtils.getRelativeTimeSpanString(time, now, DateUtils.MINUTE_IN_MILLIS,DateUtils.FORMAT_ABBREV_ALL)
        return ago.toString()
    } catch (e: Exception) {
        e.printStackTrace()
        return null
    }
}

fun Date.getRelativeDay(): String? {
    try {
        val dateToFormat = Calendar.getInstance()
        dateToFormat.time = this

        val dateToday = Calendar.getInstance()
        if(isSameDay(dateToday, dateToFormat)){
            return "Today"
        }
        if(isYesterday(dateToday,dateToFormat)){
            return "Yesterday"
        }

        return getDayMonthFormated()
    } catch (ex: Exception) {
        ex.printStackTrace()
        return null
    }
}

fun isSameDay(dateToday : Calendar,dateToFormat : Calendar): Boolean {
    val sameDay = dateToFormat.get(Calendar.DAY_OF_MONTH) == dateToday.get(Calendar.DAY_OF_MONTH)
    val sameMonth = dateToFormat.get(Calendar.MONTH) == dateToday.get(Calendar.MONTH)
    val sameYear = dateToFormat.get(Calendar.YEAR) == dateToday.get(Calendar.YEAR)

    return sameDay && sameMonth && sameYear
}

fun isYesterday(dateToday : Calendar,dateToFormat : Calendar): Boolean {
    val yesterday = dateToday.get(Calendar.DAY_OF_MONTH) - 1
    val dayToCheck = dateToFormat.get(Calendar.DAY_OF_MONTH)

    return dayToCheck == yesterday
}
