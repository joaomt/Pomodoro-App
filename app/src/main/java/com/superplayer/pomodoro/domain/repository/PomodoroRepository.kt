package com.superplayer.pomodoro.domain.repository

import com.superplayer.pomodoro.domain.model.Pomodoro

interface PomodoroRepository {
    suspend fun new(pomodoro: Pomodoro) : Long

    suspend fun getCurrentRunning() : Pomodoro?

    suspend fun get(idPomodoro: Long) : Pomodoro?

    suspend fun updateCurrentRunning(pomodoro : Pomodoro)

    suspend fun getHistory() : List<Pomodoro>?

}