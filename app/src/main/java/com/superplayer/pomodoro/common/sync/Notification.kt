package com.superplayer.pomodoro.common.sync

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.superplayer.pomodoro.R
import com.superplayer.pomodoro.common.extensions.playSoundPhone
import com.superplayer.pomodoro.main.MainActivity

object Notification {
    fun show(context: Context, title: String,withSound : Boolean = true){
        val intent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent: PendingIntent = PendingIntent.getActivity(context, 0, intent, 0)


        val channelId = context.getString(R.string.notification_channel_id)
        val builder = NotificationCompat.Builder(context, channelId)
            .setChannelId(channelId)
            .setSmallIcon(R.drawable.ic_timer)
            .setContentTitle(title)
            .setPriority(NotificationCompat.PRIORITY_MAX)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)


        with(NotificationManagerCompat.from(context)) {
            if(withSound)context.playSoundPhone()
            notify(10, builder.build())
        }
    }
}