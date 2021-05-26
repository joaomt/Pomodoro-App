package com.superplayer.pomodoro.common.sync

import android.app.AlarmManager
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import android.util.Log
import androidx.core.content.ContextCompat
import com.superplayer.pomodoro.R
import com.superplayer.pomodoro.common.coroutines.Coroutines
import com.superplayer.pomodoro.common.extensions.formatTime
import com.superplayer.pomodoro.common.extensions.getTimeRelative
import com.superplayer.pomodoro.domain.model.Pomodoro
import com.superplayer.pomodoro.domain.model.PomodoroState
import com.superplayer.pomodoro.domain.usecase.GetPomodoroUseCase
import com.superplayer.pomodoro.domain.usecase.SavePomodoroCurrentRunningUseCase
import dagger.hilt.android.AndroidEntryPoint
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class BroadcastPomodoro : HiltBroadcastReceiver() {
    @Inject
    lateinit var getPomodoroUseCase: GetPomodoroUseCase
    @Inject
    lateinit var savePomodoroCurrentRunningUseCase: SavePomodoroCurrentRunningUseCase

    override fun onReceive(context: Context, intent: Intent) {
        super.onReceive(context, intent)
        Coroutines.main {
            val id = intent.getLongExtra(KEY_ID_POMODORO, 0)
            val pomodoro = getPomodoroUseCase(idPomodoro = id)

            pomodoro?.apply {
                if (state == PomodoroState.Running) {
                    addedHistoryAt = Date()
                    state = PomodoroState.Finished
                    runningTime = 0
                    savePomodoroCurrentRunningUseCase(this)

                    val title = context.getString(R.string.notification_title)
                    Notification.show(context, title)
                }
            }
        }
    }

    companion object {
        const val ACTION_RECEIVER_STATE = "ACTION_RECEIVER_STATE"
        private const val KEY_ID_POMODORO = "KEY_ID_POMODORO"
        private var alarmMgr: AlarmManager? = null
        private lateinit var alarmIntent: PendingIntent
        private lateinit var dateWakeUp: Date

        fun createAlarmManager(context: Context, idPomodoro: Long) {
            setupAlarmManager(context, idPomodoro)
            setTriggerAlarmManager()
        }

        private fun setupAlarmManager(context: Context, idPomodoro: Long) {
            alarmMgr = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

            alarmIntent = Intent(context, BroadcastPomodoro::class.java).let { intent ->
                intent.putExtra(KEY_ID_POMODORO, idPomodoro)
                PendingIntent.getBroadcast(
                    context,
                    idPomodoro.toInt(),
                    intent,
                    PendingIntent.FLAG_ONE_SHOT
                )
            }
        }

        private fun setTriggerAlarmManager() {
            val triggerAtMillis = Date().time + Pomodoro.TIME_BASE_COUNT_DOWN
            dateWakeUp = Date(triggerAtMillis)
            alarmMgr?.set(
                AlarmManager.RTC_WAKEUP,
                triggerAtMillis,
                alarmIntent
            )
        }
    }
}