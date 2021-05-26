package com.superplayer.pomodoro.features.pomodoro.viewState

import com.superplayer.pomodoro.features.pomodoro.viewState.ActionPomodoroViewState.Companion.showAsActionStart
import com.superplayer.pomodoro.features.pomodoro.viewState.ActionPomodoroViewState.Companion.showAsActionStop

data class PomodoroViewState(
    var actionPomodoro: ActionPomodoroViewState = showAsActionStart(),
    var progressPomodoro: ProgressPomodoroViewState = ProgressPomodoroViewState.default(),
    var animationProgressPomodoro: AnimationProgressPomodoroViewState = AnimationProgressPomodoroViewState.default()
) {
    fun default() = copy(
        actionPomodoro = showAsActionStart(),
        progressPomodoro = ProgressPomodoroViewState.default(),
        animationProgressPomodoro = AnimationProgressPomodoroViewState.default()
    )

    fun running() = copy(
        actionPomodoro = showAsActionStop(),
        progressPomodoro = ProgressPomodoroViewState.running(),
        animationProgressPomodoro = AnimationProgressPomodoroViewState.running()
    )
}