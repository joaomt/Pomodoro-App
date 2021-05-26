package com.superplayer.pomodoro.domain.usecase

import com.superplayer.pomodoro.domain.model.Pomodoro
import com.superplayer.pomodoro.domain.model.PomodoroState
import com.superplayer.pomodoro.domain.repository.PomodoroRepository

class SavePomodoroCurrentRunningUseCase(private val pomodoroRepository: PomodoroRepository) {

    suspend operator fun invoke(pomodoro: Pomodoro): Pomodoro {

        if (pomodoro.id == null) {
            pomodoro.id = pomodoroRepository.new(pomodoro)
            return pomodoro
        }

        pomodoroRepository.updateCurrentRunning(pomodoro)

        return when (pomodoro.state) {
            PomodoroState.Finished, PomodoroState.Stopped -> Pomodoro()
            else -> pomodoro
        }
    }
}