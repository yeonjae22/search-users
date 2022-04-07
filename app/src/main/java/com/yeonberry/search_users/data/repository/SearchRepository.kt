package com.yeonberry.search_users.data.repository

import com.yeonberry.search_users.data.model.SearchResponse
import retrofit2.Response

interface SearchRepository {
    suspend fun getUserList(q: String, page: Int): Response<SearchResponse>
}