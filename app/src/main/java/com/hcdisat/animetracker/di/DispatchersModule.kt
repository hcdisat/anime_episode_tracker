package com.hcdisat.animetracker.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Qualifier
import javax.inject.Singleton

@InstallIn(ViewModelComponent::class)
@Module
class DispatchersModule {

    @Qualifier
    annotation class IODispatcher

    @IODispatcher
    @ViewModelScoped
    @Provides
    fun providesIODispatcher(): CoroutineDispatcher = Dispatchers.IO
}