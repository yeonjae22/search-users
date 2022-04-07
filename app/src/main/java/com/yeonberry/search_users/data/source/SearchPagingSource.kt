package com.yeonberry.search_users.data.source

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.yeonberry.search_users.api.SearchService
import com.yeonberry.search_users.data.model.User

class SearchPagingSource(
    private val backend: SearchService,
    private val query: String
) : PagingSource<Int, User>() {
    override suspend fun load(
        params: LoadParams<Int>
    ): LoadResult<Int, User> {
        return try {
            val nextPageNumber = params.key ?: 1
            val response =
                backend.getUserList(q = query, perPage = params.loadSize, page = nextPageNumber)
            LoadResult.Page(
                data = response.body()?.items!!,
                prevKey = if (nextPageNumber == 1) null else nextPageNumber - 1,
                nextKey = if (response.body()?.items?.isEmpty() == true) null else nextPageNumber + (params.loadSize / 10)
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, User>): Int? {
        return null
    }
}