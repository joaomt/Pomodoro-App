package com.superplayer.pomodoro.di

import com.superplayer.pomodoro.data.db.room.AppRoomDataBase
import com.superplayer.pomodoro.data.db.adapter.DomainToRoomAdapter
import com.superplayer.pomodoro.data.db.adapter.RoomToDomainAdapter
import com.superplayer.pomodoro.data.repository.PomodoroRepositoryImpl
import com.superplayer.pomodoro.domain.repository.PomodoroRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun providesPomodoroRepository(
        appRoomDataBase: AppRoomDataBase,
        toRoom: DomainToRoomAdapter,
        toDomain: RoomToDomainAdapter
    ): PomodoroRepository = PomodoroRepositoryImpl(appRoomDataBase, toRoom, toDomain)

}