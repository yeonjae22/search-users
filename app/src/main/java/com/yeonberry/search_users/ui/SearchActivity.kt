package com.yeonberry.search_users.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.yeonberry.search_users.R
import com.yeonberry.search_users.databinding.ActivitySearchBinding
import com.yeonberry.search_users.util.VerticalSpaceItemDecoration
import com.yeonberry.search_users.util.dpToPx
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySearchBinding
    private val searchViewModel: SearchViewModel by viewModels()
    private lateinit var searchAdapter: SearchAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initAdapter()
        initListener()
        showUserList()
        showErrorMessage()
    }

    private fun initAdapter() {
        searchAdapter = SearchAdapter { user ->
            try {
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse(user.url)
                startActivity(intent)
            } catch (e: Exception) {
                Toast.makeText(this, R.string.browser_error_toast_message, Toast.LENGTH_SHORT)
                    .show()
            }
        }

        binding.rvSearch.apply {
            adapter = searchAdapter
            addItemDecoration(VerticalSpaceItemDecoration(dpToPx(context, 12)))
        }
    }

    private fun initListener() {
        binding.ivSearch.setOnClickListener {
            searchViewModel.getUserList(binding.edtSearch.text.toString(), 1)
        }
    }

    private fun showUserList() {
        searchViewModel.userList.observe(this) { userList ->
            if (userList.isEmpty()) {
                binding.layoutEmpty.visibility = View.VISIBLE
                binding.rvSearch.visibility = View.GONE
            } else {
                binding.layoutEmpty.visibility = View.GONE
                binding.rvSearch.visibility = View.VISIBLE
                searchAdapter.submitList(userList)
            }
        }
    }

    private fun showErrorMessage() {
        searchViewModel.errorMessage.observe(this) { error ->
            Toast.makeText(this, error, Toast.LENGTH_SHORT).show()
        }
    }
}