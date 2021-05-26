package com.superplayer.pomodoro.features.pomodoro

import android.os.CountDownTimer
import androidx.lifecycle.*
import com.superplayer.pomodoro.common.coroutines.Coroutines
import com.superplayer.pomodoro.common.extensions.formatTime
import com.superplayer.pomodoro.common.extensions.formatToSeconds
import com.superplayer.pomodoro.common.livedata.SingleLiveEvent
import com.superplayer.pomodoro.common.sync.BroadcastPomodoro
import com.superplayer.pomodoro.domain.model.Pomodoro
import com.superplayer.pomodoro.domain.model.Pomodoro.Companion.COUNT_DOWN_INTERVAL
import com.superplayer.pomodoro.domain.model.PomodoroState
import com.superplayer.pomodoro.domain.usecase.GetPomodoroCurrentRunningUseCase
import com.superplayer.pomodoro.domain.usecase.SavePomodoroCurrentRunningUseCase
import com.superplayer.pomodoro.features.pomodoro.viewState.PomodoroViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class PomodoroViewModel @Inject constructor(
    private val getPomodoroCurrentRunningUseCase: GetPomodoroCurrentRunningUseCase,
    private val savePomodoroCurrentRunningUseCase: SavePomodoroCurrentRunningUseCase
) : ViewModel() {
    private lateinit var countDownTimer : CountDownTimer
    private var pomodoro = Pomodoro()

    private val _timeProgress = MutableLiveData(Pomodoro.TIME_BASE_COUNT_DOWN.formatTime())
    val timeProgress: LiveData<String> = _timeProgress

    private val _pomodoroViewState = MutableLiveData<PomodoroViewState>(PomodoroViewState())

    val pomodoroViewState: LiveData<PomodoroViewState> = _pomodoroViewState

    private val _timeProgressValue = MutableLiveData(0)
    val timeProgressValue: LiveData<Int> = _timeProgressValue

    private val _pomodoroFinished = SingleLiveEvent<Boolean>()
    val pomodoroFinished: LiveData<Boolean> = _pomodoroFinished

    private val _sendBroadcast = SingleLiveEvent<String>()
    val sendBroadcast: LiveData<String> = _sendBroadcast

    private val _createAlarmManager = SingleLiveEvent<Long>()
    val createAlarmManager: LiveData<Long> = _createAlarmManager

    init {
        fetchPomodoroCurrentRunning()
    }

    private fun fetchPomodoroCurrentRunning(){
        Coroutines.main {
            pomodoro = getPomodoroCurrentRunningUseCase()
            checkStatePomodoro()
        }
    }

    private fun checkStatePomodoro() {
        when(pomodoro.state) {
            PomodoroState.Running -> {
                resumePomodoro()
            }
        }
    }

    fun handleStartStopPomodoro() {
        when(pomodoro.state) {
            PomodoroState.Default -> startPomodoro()
            PomodoroState.Running -> stopPomodoro()
        }
    }

    private fun startPomodoro(){
        _pomodoroViewState.value = _pomodoroViewState.value?.running()
        pomodoro.apply {
            state = PomodoroState.Running
            startedAt = Date()
        }
        setupCountDownTimer(Pomodoro.TIME_BASE_COUNT_DOWN)
        savePomodoro()
    }

    private fun finishPomodoro(statePomodoro : PomodoroState) {
        _pomodoroViewState.value = _pomodoroViewState.value?.default()
        pomodoro.apply {
            state = statePomodoro
            addedHistoryAt = Date()
        }
        savePomodoro()
        releaseViewState()

        when(statePomodoro){
            PomodoroState.Finished -> dispatchAlertsPomodoroFinished()
            PomodoroState.Stopped ->  _sendBroadcast.apply {
                call()
                value= BroadcastPomodoro.ACTION_RECEIVER_STATE
            }
        }
    }

    private fun stopPomodoro() {
        finishPomodoro(PomodoroState.Stopped)
        countDownTimer.cancel()
    }

    private fun resumePomodoro(){
        _pomodoroViewState.value = _pomodoroViewState.value?.running()
        val runTime = pomodoro.getTimeActual()
        setupCountDownTimer(runTime)
    }

    private fun savePomodoro() {
        Coroutines.main {
            pomodoro = savePomodoroCurrentRunningUseCase(pomodoro)
            dispatchAlarmManager()
        }
    }

    private fun dispatchAlarmManager() {
        if(pomodoro.state == PomodoroState.Running){
            _createAlarmManager.apply {
                call()
                value = pomodoro.id
            }
        }
    }


    private fun releaseViewState() {
        _timeProgress.value = Pomodoro.TIME_BASE_COUNT_DOWN.formatTime()
        _timeProgressValue.value = 0
    }

    private fun setupCountDownTimer(runTime : Long) {
        countDownTimer = object : CountDownTimer(runTime, COUNT_DOWN_INTERVAL) {
            override fun onTick(millisUntilFinished: Long) {
                pomodoro.runningTime = millisUntilFinished
                _timeProgress.value = millisUntilFinished.formatTime()
                _timeProgressValue.value = Pomodoro.TIME_BASE_COUNT_DOWN.formatToSeconds() - millisUntilFinished.formatToSeconds()
            }

            override fun onFinish() {
                pomodoro.runningTime = 0
                finishPomodoro(PomodoroState.Finished)
            }
        }.start()
    }

    private fun dispatchAlertsPomodoroFinished() {
        _sendBroadcast.apply {
            call()
            value= BroadcastPomodoro.ACTION_RECEIVER_STATE
        }
        viewModelScope.launch {
            delay(1500)
            _pomodoroFinished.apply {
                call()
                value = true
            }
        }
    }

}

