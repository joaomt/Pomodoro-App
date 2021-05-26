package com.superplayer.pomodoro.domain.model

import java.util.*

data class Pomodoro(
    var id : Long?=null,
    var state : PomodoroState?=PomodoroState.Default,
    var startedAt : Date?=null,
    var runningTime : Long?=null,
    var addedHistoryAt : Date?=null
){
    fun isRunningTimeExceeded() : Boolean{
        val timeRunning = getTimeRunning()

        val isExceeded = timeRunning > TIME_BASE_COUNT_DOWN
        return isExceeded && state == PomodoroState.Running
    }

    private fun getTimeRunning() : Long {
        val timeRunning = Date().time - (startedAt?.time ?: Date().time)
        return timeRunning
    }

    fun getTimeActual() : Long {
        return TIME_BASE_COUNT_DOWN - getTimeRunning()
    }

    fun getRunTimeReal() : Long{
        if(runningTime?:0L == 0L)return TIME_BASE_COUNT_DOWN

        return TIME_BASE_COUNT_DOWN - ((runningTime?:0L)-COUNT_DOWN_INTERVAL)
    }

    companion object{
        const val TIME_BASE_COUNT_DOWN: Long = 1500000
        const val COUNT_DOWN_INTERVAL: Long = 1000
    }
}