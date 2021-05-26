package com.superplayer.pomodoro.common.sync

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.annotation.CallSuper

abstract class HiltBroadcastReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {}
}