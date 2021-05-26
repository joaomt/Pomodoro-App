package com.superplayer.pomodoro.data.db.adapter

import com.superplayer.pomodoro.data.db.entity.DBPomodoro
import com.superplayer.pomodoro.domain.model.Pomodoro
import com.superplayer.pomodoro.domain.model.PomodoroState
import java.util.*


class DomainToRoomAdapter {

    internal fun pomodoro(pomodoro: Pomodoro) =
        DBPomodoro(
            state = pomodoro.state?.name,
            startedAt = pomodoro.startedAt?.time,
            runningTime = pomodoro.runningTime,
            addedHistoryAt = pomodoro.addedHistoryAt?.time
        )
}