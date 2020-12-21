package com.example.repository.di

import com.example.repository.CompBuilderRepository
import com.example.repository.CompBuilderRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent

@Module
@InstallIn(ApplicationComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun provideRepository(compBuilderRepositoryImpl: CompBuilderRepositoryImpl): CompBuilderRepository

}