package com.superplayer.pomodoro.features.pomodoro.viewState

import android.view.View
import androidx.annotation.ColorRes
import com.superplayer.pomodoro.R

data class AnimationProgressPomodoroViewState(
    val playAnimation: Boolean = false,
    val visibility : Int = View.GONE
) {
    companion object {
        fun default() =
            AnimationProgressPomodoroViewState(
                playAnimation = false,
                visibility = View.GONE
            )

        fun running() =
            AnimationProgressPomodoroViewState(
                playAnimation = true,
                visibility = View.VISIBLE
            )
    }
}