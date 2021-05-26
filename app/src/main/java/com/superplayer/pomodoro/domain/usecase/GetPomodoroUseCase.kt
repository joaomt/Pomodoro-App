package com.superplayer.pomodoro.domain.usecase

import com.superplayer.pomodoro.domain.model.Pomodoro
import com.superplayer.pomodoro.domain.model.PomodoroState
import com.superplayer.pomodoro.domain.repository.PomodoroRepository

class GetPomodoroUseCase(
    private val pomodoroRepository: PomodoroRepository) {

    suspend operator fun invoke(idPomodoro : Long): Pomodoro? {
        val pomodoro = pomodoroRepository.get(idPomodoro)

        return pomodoro
    }
}