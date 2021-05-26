package com.superplayer.pomodoro.data.db.dao

import androidx.room.*
import com.superplayer.pomodoro.data.db.entity.DBPomodoro
import com.superplayer.pomodoro.domain.model.Pomodoro
import com.superplayer.pomodoro.domain.model.PomodoroState

@Dao
interface PomodoroDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(dbPomodoro: DBPomodoro): Long

    @Update
    suspend fun update(dbPomodoro: DBPomodoro)

    @Query("SELECT * FROM pomodoro WHERE uuid = :id")
    suspend fun get(id : Long) : DBPomodoro?

    @Query("SELECT * FROM pomodoro WHERE state = 'running'")
    suspend fun getRunning() : DBPomodoro?

    @Query("SELECT * FROM pomodoro")
    suspend fun getAll() : List<DBPomodoro>?
}