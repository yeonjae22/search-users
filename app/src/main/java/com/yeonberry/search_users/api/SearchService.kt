package com.yeonberry.search_users.api

import com.yeonberry.search_users.data.model.SearchResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface SearchService {
    @GET("/search/users")
    suspend fun getUserList(
        @Header("accept") accept: String? = "application/vnd.github.v3+json",
        @Query("q") q: String,
        @Query("sort") sort: String? = "best match",
        @Query("order") order: String? = "desc",
        @Query("per_page") perPage: Int,
        @Query("page") page: Int
    ): Response<SearchResponse>
}