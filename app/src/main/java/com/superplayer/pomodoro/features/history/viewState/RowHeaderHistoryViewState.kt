package com.superplayer.pomodoro.features.history.viewState

import com.superplayer.pomodoro.R

data class RowHeaderHistoryViewState(
    var titleHeaderRowItem: String
) : RowHistoryViewStateFactory() {
    override fun getLayoutItemType(): Int = R.layout.item_group_history
}