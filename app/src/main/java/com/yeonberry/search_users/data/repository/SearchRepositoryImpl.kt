package com.yeonberry.search_users.data.repository

import com.yeonberry.search_users.api.SearchService
import com.yeonberry.search_users.data.model.SearchResponse
import retrofit2.Response
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(private val api: SearchService) :
    SearchRepository {
    override suspend fun getUserList(q: String, page: Int): Response<SearchResponse> =
        api.getUserList(
            q = q,
            page = page
        )
}