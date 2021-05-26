package com.superplayer.pomodoro.features.pomodoro.viewState

import androidx.annotation.ColorRes
import com.superplayer.pomodoro.R

data class ProgressPomodoroViewState(
    @ColorRes
    val color: Int = R.color.colorBackgroundLight
) {
    companion object{
        fun default() =
            ProgressPomodoroViewState(
                color = R.color.colorBackgroundLight
            )

        fun running() =
            ProgressPomodoroViewState(
                color = R.color.colorPrimary
            )
    }
}