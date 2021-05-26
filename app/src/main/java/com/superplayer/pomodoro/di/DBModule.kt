package com.superplayer.pomodoro.di

import android.content.Context
import androidx.room.Room
import com.superplayer.pomodoro.data.db.room.AppRoomDataBase
import com.superplayer.pomodoro.data.db.adapter.DomainToRoomAdapter
import com.superplayer.pomodoro.data.db.adapter.RoomToDomainAdapter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DBModule {

    @Provides
    @Singleton
    fun providesAppRoomDataBase(@ApplicationContext context: Context): AppRoomDataBase =
        Room.databaseBuilder(context,
            AppRoomDataBase::class.java, AppRoomDataBase.NAME_DB)
            .fallbackToDestructiveMigration()
            .build()

    @Provides
    @Singleton
    fun providesDomainToRoomAdapter() = DomainToRoomAdapter()

    @Provides
    @Singleton
    fun providesRoomToDomainAdapter() = RoomToDomainAdapter()

}