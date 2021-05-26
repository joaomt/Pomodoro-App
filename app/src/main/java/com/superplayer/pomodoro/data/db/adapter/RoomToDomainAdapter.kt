package com.superplayer.pomodoro.data.db.adapter

import com.superplayer.pomodoro.data.db.entity.DBPomodoro
import com.superplayer.pomodoro.domain.model.Pomodoro
import com.superplayer.pomodoro.domain.model.PomodoroState
import java.util.*

class RoomToDomainAdapter {

    internal fun pomodoro(
        dbPomodoro: DBPomodoro): Pomodoro {
        return Pomodoro().also {
            it.id = dbPomodoro.id
            it.state = PomodoroState.fromName(dbPomodoro.state?:PomodoroState.Default.name)
            it.startedAt = dbPomodoro.startedAt?.let { dt -> Date(dt) }
            it.runningTime = dbPomodoro.runningTime
            it.addedHistoryAt = dbPomodoro.addedHistoryAt?.let { dt -> Date(dt) }
        }
    }


}
