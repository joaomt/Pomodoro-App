package com.superplayer.pomodoro.domain.model

sealed class PomodoroState(val name : String) {
    object Default : PomodoroState("default")
    object Running : PomodoroState("running")
    object Finished : PomodoroState("finished")
    object Stopped : PomodoroState("stopped")

    companion object{
        fun fromName(name: String) : PomodoroState{
            return when(name){
                Default.name -> Default
                Running.name -> Running
                Finished.name -> Finished
                Stopped.name -> Stopped
                else -> Default
            }
        }
    }
}