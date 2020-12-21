package com.example.local.di

import android.content.Context
import androidx.room.Room
import com.example.local.AppDatabase
import com.example.local.LocalDataSourceImpl
import com.example.local.dao.ChampionDao
import com.example.local.dao.CompositionDao
import com.example.repository.datasource.local.LocalDataSource
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext

@Module
@InstallIn(ApplicationComponent::class)
abstract class LocalModule {

    @Binds
    abstract fun provideLocalDataSource(localDataSourceImpl: LocalDataSourceImpl): LocalDataSource

    companion object {

        @Provides
        fun provideRoomDatabase(@ApplicationContext context: Context): AppDatabase =
            Room.databaseBuilder(context, AppDatabase::class.java, "comp-builder").build()

        @Provides
        fun provideChampionDao(appDatabase: AppDatabase): ChampionDao =
            appDatabase.championDao()

        @Provides
        fun provideCompositionDao(appDatabase: AppDatabase): CompositionDao =
            appDatabase.compositionDao()

    }

}