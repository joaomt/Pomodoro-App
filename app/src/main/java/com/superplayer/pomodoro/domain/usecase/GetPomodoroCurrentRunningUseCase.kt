package com.superplayer.pomodoro.domain.usecase

import com.superplayer.pomodoro.domain.model.Pomodoro
import com.superplayer.pomodoro.domain.model.PomodoroState
import com.superplayer.pomodoro.domain.repository.PomodoroRepository

class GetPomodoroCurrentRunningUseCase(
    private val pomodoroRepository: PomodoroRepository) {

    suspend operator fun invoke(): Pomodoro {
        val pomodoroCurrentRunning = pomodoroRepository.getCurrentRunning()

        return pomodoroCurrentRunning?.apply {
            if (isRunningTimeExceeded()) {
                state = PomodoroState.Finished
                runningTime = 0

                pomodoroRepository.updateCurrentRunning(this)
                return Pomodoro()
            }
        }?:Pomodoro()
    }
}