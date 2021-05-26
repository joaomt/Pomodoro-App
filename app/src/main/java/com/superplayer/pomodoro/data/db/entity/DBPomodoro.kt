package com.superplayer.pomodoro.data.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "pomodoro")
data class DBPomodoro(
    @ColumnInfo(name = "state") var state : String?,
    @ColumnInfo(name = "stared_at") var startedAt : Long?,
    @ColumnInfo(name = "running_time") var runningTime : Long?,
    @ColumnInfo(name = "added_history_at") var addedHistoryAt : Long?
){
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "uuid")
    var id: Long = 0L
}