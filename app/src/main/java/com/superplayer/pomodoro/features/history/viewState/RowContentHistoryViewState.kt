package com.superplayer.pomodoro.features.history.viewState

import com.superplayer.pomodoro.R

class RowContentHistoryViewState(
    var runningTimePomodoro: String,
    var statePomodoro: String,
    var timePomodoroFinished: String
) : RowHistoryViewStateFactory() {

    override fun getLayoutItemType(): Int = R.layout.item_history

}