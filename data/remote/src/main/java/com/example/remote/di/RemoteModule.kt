package com.example.remote.di

import com.example.remote.RemoteDataSourceImpl
import com.example.remote.service.RemoteService
import com.example.repository.datasource.remote.RemoteDataSource
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import okhttp3.HttpUrl
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
abstract class RemoteModule {

    companion object {
        @Provides
        fun provideBaseUrl(): HttpUrl = HttpUrl.get("https://run.mocky.io/")

        @Provides
        @Singleton
        fun provideRetrofit(baseUrl: HttpUrl): Retrofit {
            return Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }

        @Provides
        @Singleton
        fun provideRemoteService(retrofit: Retrofit): RemoteService =
            retrofit.create(RemoteService::class.java)
    }


    @Binds
    @Singleton
    abstract fun provideRemoteDataSource(remoteDataSourceImpl: RemoteDataSourceImpl): RemoteDataSource

}