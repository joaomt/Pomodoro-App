package com.superplayer.pomodoro.features.history

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.superplayer.pomodoro.R
import com.superplayer.pomodoro.common.coroutines.Coroutines
import com.superplayer.pomodoro.common.extensions.*
import com.superplayer.pomodoro.domain.model.Pomodoro
import com.superplayer.pomodoro.domain.usecase.GetHistoryUseCase
import com.superplayer.pomodoro.features.history.viewState.HistoryViewState
import com.superplayer.pomodoro.features.history.viewState.RowContentHistoryViewState
import com.superplayer.pomodoro.features.history.viewState.RowHeaderHistoryViewState
import com.superplayer.pomodoro.features.history.viewState.RowHistoryViewStateFactory
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlin.collections.ArrayList

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val getHistoryUseCase: GetHistoryUseCase
) : ViewModel() {

    private val _historyViewState = MutableLiveData<HistoryViewState>()
    val historyViewState: LiveData<HistoryViewState> = _historyViewState

    private fun onInit() {
        fetchListHistory()
    }

    fun fetchListHistory() {
        Coroutines.main {
            val result = getHistoryUseCase()
            processHistoryData(result)
        }
    }

    private fun processHistoryData(history: List<Pomodoro>) {
        val listHistoryViewState = ArrayList<RowHistoryViewStateFactory>()

        val listGroup = history.groupBy { pomodoro -> pomodoro.addedHistoryAt?.getRelativeDay() }

        listGroup.forEach { (headerGroup, list) ->
            listHistoryViewState.add(RowHeaderHistoryViewState(headerGroup?:""))
            listHistoryViewState.addAll(list.map { pomodoro -> pomodoro.mapToViewState() })
        }

        _historyViewState.value = HistoryViewState().showHistory(listHistoryViewState)
    }

    private fun Pomodoro.mapToViewState() : RowContentHistoryViewState{
        return RowContentHistoryViewState(
                    runningTimePomodoro = getRunTimeReal().formatTime(),
                    statePomodoro = state?.name ?: "",
                    timePomodoroFinished = addedHistoryAt?.getTimeRelative()?: ""
        )
    }

}