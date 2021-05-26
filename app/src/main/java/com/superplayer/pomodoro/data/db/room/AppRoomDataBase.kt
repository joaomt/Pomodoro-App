package com.superplayer.pomodoro.data.db.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.superplayer.pomodoro.data.db.dao.PomodoroDAO
import com.superplayer.pomodoro.data.db.entity.DBPomodoro

@Database(
    entities = [
        (DBPomodoro::class)
    ], version = 1)
abstract class AppRoomDataBase : RoomDatabase() {
    abstract fun pomodoroDao(): PomodoroDAO

    companion object{
        const val NAME_DB = "PomodoroAppDataBase"
    }
}