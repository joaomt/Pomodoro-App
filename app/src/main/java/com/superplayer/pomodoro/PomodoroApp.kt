package com.superplayer.pomodoro

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.media.RingtoneManager
import android.os.Build
import androidx.annotation.RequiresApi
import dagger.hilt.android.HiltAndroidApp

/**
 * [Application] definition for Pomodoro.
 */
@HiltAndroidApp
class PomodoroApp : Application() {

    override fun onCreate() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            initNotificationChannels()
        }
        super.onCreate()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun initNotificationChannels() {
        val mNotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val channelIdOne = getString(R.string.notification_channel_id)
        val nameChannel = getString(R.string.notification_description_channel)
        val descriptionChannel = getString(R.string.notification_description_channel)
        val importanceOne = NotificationManager.IMPORTANCE_HIGH

        val mChannel = NotificationChannel(channelIdOne, nameChannel, importanceOne)
        mChannel.apply {
            description = descriptionChannel
            enableLights(true)
            lightColor = getColor(R.color.colorPrimary)
            enableVibration(true)
            vibrationPattern = longArrayOf(
                100,
                200,
                300,
                400,
                500,
                400,
                300,
                200,
                400)

            setSound(null, null);

        }

        mNotificationManager.createNotificationChannel(mChannel)

    }
}