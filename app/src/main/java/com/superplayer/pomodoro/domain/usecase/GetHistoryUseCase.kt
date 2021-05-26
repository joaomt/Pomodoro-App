package com.superplayer.pomodoro.domain.usecase

import com.superplayer.pomodoro.domain.model.Pomodoro
import com.superplayer.pomodoro.domain.model.PomodoroState
import com.superplayer.pomodoro.domain.repository.PomodoroRepository

class GetHistoryUseCase(
    private val pomodoroRepository: PomodoroRepository
) {

    suspend operator fun invoke(): List<Pomodoro> {
        return pomodoroRepository.getHistory()
            ?.filter { pomodoro -> pomodoro.state == PomodoroState.Finished || pomodoro.state == PomodoroState.Stopped }
            ?.asReversed()
            ?: emptyList()
    }
}