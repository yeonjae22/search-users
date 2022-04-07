package com.yeonberry.search_users.data.model

import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("login")
    val username: String,
    @SerializedName("avatar_url")
    val imageUrl: String,
    @SerializedName("html_url")
    val url: String,
)