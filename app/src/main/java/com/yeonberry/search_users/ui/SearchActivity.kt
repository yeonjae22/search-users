package com.yeonberry.search_users.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import com.yeonberry.search_users.R
import com.yeonberry.search_users.databinding.ActivitySearchBinding
import com.yeonberry.search_users.util.VerticalSpaceItemDecoration
import com.yeonberry.search_users.util.dpToPx
import com.yeonberry.search_users.util.hideKeyboard
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySearchBinding
    private val viewModel: SearchViewModel by viewModels()
    private lateinit var searchAdapter: SearchAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initAdapter()
        initListener()
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

        searchAdapter.addLoadStateListener { loadState ->
            if (loadState.source.refresh is LoadState.NotLoading && loadState.append.endOfPaginationReached && searchAdapter.itemCount < 1) {
                binding.rvSearch.visibility = View.GONE
                binding.layoutEmpty.visibility = View.VISIBLE
            } else {
                binding.rvSearch.visibility = View.VISIBLE
                binding.layoutEmpty.visibility = View.GONE
            }
        }

        searchAdapter.registerAdapterDataObserver(object : RecyclerView.AdapterDataObserver() {
            override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                if (positionStart == 0) {
                    binding.rvSearch.scrollToPosition(0)
                }
            }
        })
    }

    private fun initListener() {
        binding.ivSearch.setOnClickListener {
            getUserList()
        }

        binding.edtSearch.setOnKeyListener { _, keyCode, keyEvent ->
            if ((keyEvent.action == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                getUserList()
                true
            } else {
                false
            }
        }
    }

    private fun getUserList() {
        lifecycleScope.launch {
            viewModel.getUserList(binding.edtSearch.text.toString())
                .observe(this@SearchActivity) {
                    searchAdapter.submitData(lifecycle, it)
                }
        }
        binding.edtSearch.hideKeyboard()
    }
}