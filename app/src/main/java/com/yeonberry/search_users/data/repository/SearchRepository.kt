package com.yeonberry.search_users.data.repository

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.yeonberry.search_users.data.model.User

interface SearchRepository {
    suspend fun getUserList(q: String): LiveData<PagingData<User>>
}