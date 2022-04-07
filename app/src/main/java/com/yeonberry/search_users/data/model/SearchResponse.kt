package com.yeonberry.search_users.data.model

import com.google.gson.annotations.SerializedName

data class SearchResponse(
    @SerializedName("total_count")
    val totalCount: Int,
    @SerializedName("items")
    val items: List<User>,
)