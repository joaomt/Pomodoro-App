package com.superplayer.pomodoro.features.history.viewState

import com.superplayer.pomodoro.common.extensions.addList

data class HistoryViewState(
    val listHistory: List<RowHistoryViewStateFactory> = emptyList()
) {
    fun showHistory(historyList: List<RowHistoryViewStateFactory>) = copy(
        listHistory = listHistory.toMutableList().addList(historyList)
    )
}