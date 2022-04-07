package com.yeonberry.search_users.di

import com.yeonberry.search_users.api.SearchService
import com.yeonberry.search_users.data.repository.SearchRepository
import com.yeonberry.search_users.data.repository.SearchRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
class RepositoryModule {

    @Provides
    @ViewModelScoped
    fun provideSearchRepository(
        searchService: SearchService
    ): SearchRepository {
        return SearchRepositoryImpl(
            searchService
        )
    }
}