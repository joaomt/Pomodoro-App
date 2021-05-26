package com.superplayer.pomodoro.common.extensions

import android.app.Activity
import android.content.Context
import android.media.AudioManager
import android.media.MediaPlayer
import android.media.RingtoneManager
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.util.Log
import android.util.TypedValue
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

fun <T> LiveData<T>.observeValue(owner: LifecycleOwner, observer: (t: T) -> Unit) {
    this.observe(owner, Observer { newVal: T ->
        newVal?.let(observer)
    })
}

fun <T> MutableList<T>.addList(toAdd: List<T>): List<T> {
    addAll(toAdd)
    return this
}