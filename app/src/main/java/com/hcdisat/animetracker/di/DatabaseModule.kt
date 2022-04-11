package com.hcdisat.animetracker.di

import android.content.Context
import androidx.room.Room
import com.hcdisat.animetracker.data.database.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Singleton
    @Provides
    fun providesDatabaseRepository(animeDao: AnimeDao): IDatabaseRepository =
        DatabaseRepository(animeDao)

    @Singleton
    @Provides
    fun providesAnimesDao(database: AnimeDatabase): AnimeDao = database.animeDao()

    @Singleton
    @Provides
    fun providesAnimeDatabase(@ApplicationContext context: Context): AnimeDatabase =
        Room.databaseBuilder(
            context,
            AnimeDatabase::class.java,
            DB_NAME).build()
}