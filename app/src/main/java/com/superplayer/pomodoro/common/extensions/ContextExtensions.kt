package com.superplayer.pomodoro.common.extensions

import android.content.Context
import android.media.AudioManager
import android.media.MediaPlayer
import android.media.RingtoneManager
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.util.TypedValue
import androidx.fragment.app.Fragment

fun Fragment.pxTodp(dp_p: Int): Int {
    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        dp_p.toFloat(),
        resources.displayMetrics
    ).toInt()
}

fun Context.vibratePhone() {
    try {
        val timeVibration = 1000L
        val v = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            v.vibrate(VibrationEffect.createOneShot(timeVibration, VibrationEffect.DEFAULT_AMPLITUDE))
        } else {
            v.vibrate(timeVibration)
        }
    } catch (ex: Exception) {
        ex.printStackTrace()
    }
}

fun Context.playSoundPhone() {
    try {
        val tone = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)

        val player = MediaPlayer.create(this, tone)
        val audioManager = getSystemService(Context.AUDIO_SERVICE) as AudioManager
        audioManager.setStreamVolume(
            AudioManager.STREAM_MUSIC, audioManager.getStreamMaxVolume(
                AudioManager.STREAM_MUSIC), 0)

        player.start()
    } catch (ex: java.lang.Exception) {
        ex.printStackTrace()
    }
}