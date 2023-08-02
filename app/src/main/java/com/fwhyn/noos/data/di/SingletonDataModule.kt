package com.fwhyn.noos.data.di

import com.fwhyn.noos.data.api.NewsClient
import com.fwhyn.noos.data.local.CategoryLocalDataSource
import com.fwhyn.noos.data.models.*
import com.fwhyn.noos.data.remote.ArticleRemoteDataSource
import com.fwhyn.noos.data.remote.SourceRemoteDataSource
import com.fwhyn.noos.data.repository.ArticleDataRepository
import com.fwhyn.noos.data.repository.BaseDataRepository
import com.fwhyn.noos.data.repository.CategoryDataRepository
import com.fwhyn.noos.data.repository.SourceDataRepository
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

    @Provides
    @Singleton
    fun provideCategoryDataRepository(
        categoryLocalDataSource: CategoryLocalDataSource
    ): BaseDataRepository<Unit, List<Category>> {
        return CategoryDataRepository(categoryLocalDataSource)
    }

    @Provides
    @Singleton
    fun provideCategoryLocalDataSource(): CategoryLocalDataSource {
        return CategoryLocalDataSource()
    }

    @Provides
    @Singleton
    fun provideSourceDataRepository(
        sourceRemoteDataSource: SourceRemoteDataSource
    ): BaseDataRepository<SourceRequestParameter, List<Source>> {
        return SourceDataRepository(sourceRemoteDataSource)
    }

    @Provides
    @Singleton
    fun provideSourceRemoteDataSource(
        client: NewsClient
    ): SourceRemoteDataSource {
        return SourceRemoteDataSource(client)
    }

    @Provides
    @Singleton
    fun provideArticleDataRepository(
        articleRemoteDataSource: ArticleRemoteDataSource
    ): BaseDataRepository<ArticleRequestParameter, List<Article>> {
        return ArticleDataRepository(articleRemoteDataSource)
    }

    @Provides
    @Singleton
    fun provideArticleRemoteDataSource(
        client: NewsClient
    ): ArticleRemoteDataSource {
        return ArticleRemoteDataSource(client)
    }
}