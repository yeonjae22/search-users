package com.yeonberry.search_users.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yeonberry.search_users.data.model.User
import com.yeonberry.search_users.data.repository.SearchRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val repository: SearchRepository
) : ViewModel() {

    private val _userList = MutableLiveData<List<User>>()
    val userList: LiveData<List<User>> get() = _userList

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> get() = _errorMessage

    fun getUserList(q: String, page: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val response = repository.getUserList(q, page)
            if (response.isSuccessful) {
                _userList.postValue(response.body()?.items)
            } else {
                _errorMessage.postValue(response.code().toString())
            }
        }
    }
}