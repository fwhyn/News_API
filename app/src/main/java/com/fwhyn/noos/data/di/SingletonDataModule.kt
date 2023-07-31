package com.fwhyn.noos.data.di

import com.fwhyn.noos.data.api.NewsClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class SingletonDataModule {

    @Provides
    @Singleton
    fun provideNewsClient(): NewsClient {
        return NewsClient()
    }
}