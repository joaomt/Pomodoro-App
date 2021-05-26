package com.superplayer.pomodoro.features.pomodoro.viewState

import androidx.annotation.StringRes
import com.superplayer.pomodoro.R

data class ActionPomodoroViewState(
    @StringRes
    val title: Int = R.string.button_start,
) {
    companion object {
        fun showAsActionStart() =
            ActionPomodoroViewState(
                title = R.string.button_start
            )

        fun showAsActionStop() =
            ActionPomodoroViewState(
                title = R.string.button_stop
            )
    }

}