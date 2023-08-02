package com.fwhyn.noos.ui.main

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.fwhyn.noos.R
import com.fwhyn.noos.basecomponent.baseclass.BaseActivityBinding
import com.fwhyn.noos.data.models.Category
import com.fwhyn.noos.databinding.ActivityMainBinding
import com.fwhyn.noos.ui.helper.Constants.CATEGORY
import com.fwhyn.noos.ui.helper.CustomResult
import com.fwhyn.noos.ui.helper.enableSwipeRefresh
import com.fwhyn.noos.ui.sources.SourcesActivity
import dagger.hilt.android.AndroidEntryPoint

// TODO kasih warna di category
// TODO kasih warna di source
// TODO kasih info kalo isi berita kosong
// TODO search feature

@AndroidEntryPoint
class MainActivity : BaseActivityBinding<ActivityMainBinding>() {

    private lateinit var categoriesView: RecyclerView
    private lateinit var categoryAdapter: CategoryAdapter
    private val categories = ArrayList<Category>()

    private val viewModel: MainViewModel by viewModels()
    override fun onBinding(): ActivityMainBinding {
        return ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        init()
    }

    // ----------------------------------------------------------------
    private fun init() {
        initObserver()
        initView()
    }

    private fun initObserver() {
        viewModel.category.observe(this) {
            when (it) {
                is CustomResult.Failure -> {}
                CustomResult.Loading -> {}
                is CustomResult.Success -> onSuccess(it)
            }
        }
    }

    private fun initView() {
        initTitle()
        initSwipeRefresh()
        initCategoriesView()
    }

    private fun initTitle() {
        viewBinding.tvSourceTitle.text = getString(R.string.category)
    }

    private fun initSwipeRefresh() {
        viewBinding.layoutSwipeRefresh.enableSwipeRefresh(false)
    }

    private fun initCategoriesView() {
        categoriesView = viewBinding.recyclerViewNews

        categoryAdapter = CategoryAdapter(this@MainActivity, categories) { category ->
            onItemClick(category)
        }

        categoriesView.run {
            layoutManager = GridLayoutManager(applicationContext, 2)
            adapter = categoryAdapter
            addItemDecoration(DividerItemDecoration(this@MainActivity, DividerItemDecoration.VERTICAL))
        }
    }

    private fun onItemClick(category: Category) {
        val intent = Intent(this@MainActivity, SourcesActivity::class.java)

        intent.putExtra(CATEGORY, category)
        startActivity(intent)
    }

    private fun onSuccess(data: CustomResult.Success<List<Category>>) {
        showCategoryList(data.value)
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun showCategoryList(data: List<Category>) {
        categories.clear()
        categories.addAll(data)
        categoryAdapter.notifyDataSetChanged()
    }
}