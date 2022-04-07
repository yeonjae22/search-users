package com.yeonberry.search_users.data.repository

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.yeonberry.search_users.api.SearchService
import com.yeonberry.search_users.data.model.User
import com.yeonberry.search_users.data.source.SearchPagingSource
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(private val api: SearchService) :
    SearchRepository {
    override suspend fun getUserList(q: String): LiveData<PagingData<User>> {
        return Pager(
            config = PagingConfig(pageSize = 10, enablePlaceholders = false),
            pagingSourceFactory = { SearchPagingSource(api, q) }
        ).liveData
    }
}