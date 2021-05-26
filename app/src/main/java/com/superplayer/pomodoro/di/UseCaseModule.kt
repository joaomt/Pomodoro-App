package com.superplayer.pomodoro.di

import com.superplayer.pomodoro.domain.repository.PomodoroRepository
import com.superplayer.pomodoro.domain.usecase.GetHistoryUseCase
import com.superplayer.pomodoro.domain.usecase.GetPomodoroCurrentRunningUseCase
import com.superplayer.pomodoro.domain.usecase.GetPomodoroUseCase
import com.superplayer.pomodoro.domain.usecase.SavePomodoroCurrentRunningUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    @Singleton
    fun providesGetPomodoroUseCase(pomodoroRepository: PomodoroRepository) : GetPomodoroUseCase = GetPomodoroUseCase(pomodoroRepository)

    @Provides
    @Singleton
    fun providesGetCurrentRunningUseCase(pomodoroRepository: PomodoroRepository) : GetPomodoroCurrentRunningUseCase = GetPomodoroCurrentRunningUseCase(pomodoroRepository)

    @Provides
    @Singleton
    fun providesSaveCurrentRunningUseCase(pomodoroRepository: PomodoroRepository) : SavePomodoroCurrentRunningUseCase = SavePomodoroCurrentRunningUseCase(pomodoroRepository)

    @Provides
    @Singleton
    fun providesGetHistoryUseCase(pomodoroRepository: PomodoroRepository) : GetHistoryUseCase = GetHistoryUseCase(pomodoroRepository)

}