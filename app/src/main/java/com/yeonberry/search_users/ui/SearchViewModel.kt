package com.yeonberry.search_users.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagingData
import com.yeonberry.search_users.data.model.User
import com.yeonberry.search_users.data.repository.SearchRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val repository: SearchRepository
) : ViewModel() {

    suspend fun getUserList(q: String): LiveData<PagingData<User>> {
        return repository.getUserList(q)
    }
}