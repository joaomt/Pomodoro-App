package com.superplayer.pomodoro.data.repository

import com.superplayer.pomodoro.data.db.room.AppRoomDataBase
import com.superplayer.pomodoro.data.db.adapter.DomainToRoomAdapter
import com.superplayer.pomodoro.data.db.adapter.RoomToDomainAdapter
import com.superplayer.pomodoro.domain.model.Pomodoro
import com.superplayer.pomodoro.domain.model.PomodoroState
import com.superplayer.pomodoro.domain.repository.PomodoroRepository

class PomodoroRepositoryImpl(
    appRoomDataBase: AppRoomDataBase,
    private val toRoom: DomainToRoomAdapter,
    private val toDomain: RoomToDomainAdapter
) : PomodoroRepository {
    private val pomodoroDao = appRoomDataBase.pomodoroDao()

    override suspend fun new(pomodoro: Pomodoro): Long {
        val pomodoroDb = toRoom.pomodoro(pomodoro)

       return pomodoroDao.insert(pomodoroDb)
    }

    override suspend fun getCurrentRunning(): Pomodoro? {
        val pomodoroDb = pomodoroDao.getRunning()

        return pomodoroDb?.let { it ->
            toDomain.pomodoro(it)
        }
    }

    override suspend fun get(idPomodoro: Long): Pomodoro? {
        val pomodoroDb = pomodoroDao.get(idPomodoro)

        return pomodoroDb?.let { it ->
            toDomain.pomodoro(it)
        }
    }

    override suspend fun updateCurrentRunning(pomodoro: Pomodoro) {
        val pomodoroDb = toRoom.pomodoro(pomodoro)
        pomodoroDb.id = pomodoro.id!!

        pomodoroDao.update(pomodoroDb)
    }

    override suspend fun getHistory(): List<Pomodoro>? {
        val historyListDb = pomodoroDao.getAll()

        if(historyListDb.isNullOrEmpty())return null

        return historyListDb.map {
            toDomain.pomodoro(it)
        }
    }

}