package com.fwhyn.noos.ui.sources

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.fwhyn.noos.R
import com.fwhyn.noos.basecomponent.baseclass.BaseActivityBinding
import com.fwhyn.noos.data.helper.getSerializable
import com.fwhyn.noos.data.models.Category
import com.fwhyn.noos.data.models.Source
import com.fwhyn.noos.databinding.ActivityMainBinding
import com.fwhyn.noos.databinding.ViewErrorBinding
import com.fwhyn.noos.ui.articles.ArticlesActivity
import com.fwhyn.noos.ui.helper.Constants.CATEGORY
import com.fwhyn.noos.ui.helper.Constants.SOURCE
import com.fwhyn.noos.ui.helper.CustomResult
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SourcesActivity : BaseActivityBinding<ActivityMainBinding>(), SwipeRefreshLayout.OnRefreshListener {

    private lateinit var swipeRefresh: SwipeRefreshLayout
    private lateinit var errorView: ViewErrorBinding

    private lateinit var sourcesView: RecyclerView
    private lateinit var sourceAdapter: SourceAdapter
    private val sources = ArrayList<Source>()

    private val viewModel: SourcesViewModel by viewModels()
    override fun onBinding(): ActivityMainBinding {
        return ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        init(savedInstanceState)
    }

    // ----------------------------------------------------------------
    private fun init(savedInstanceState: Bundle?) {
        initObserver()
        initView()

        if (savedInstanceState == null) {
            loadSources()
        }
    }

    private fun initObserver() {
        viewModel.sources.observe(this) {
            when (it) {
                is CustomResult.Failure -> onFailure(it)
                CustomResult.Loading -> onLoading()
                is CustomResult.Success -> onSuccess(it)
            }
        }
    }

    private fun initView() {
        initTitle()
        initSwipeRefresh()
        initArticlesView()
        initErrorView()
    }

    private fun initTitle() {
        viewBinding.tvSourceTitle.text = getString(R.string.source)
    }

    private fun initSwipeRefresh() {
        swipeRefresh = viewBinding.layoutSwipeRefresh

        swipeRefresh.run {
            setOnRefreshListener(this@SourcesActivity)
            setColorSchemeResources(R.color.gray)
        }
    }

    private fun initArticlesView() {
        sourcesView = viewBinding.recyclerViewNews
        sourceAdapter = SourceAdapter(this@SourcesActivity, sources) { source ->
            onItemClick(source)
        }

        sourcesView.run {
            layoutManager = LinearLayoutManager(this@SourcesActivity)
            adapter = sourceAdapter
            addItemDecoration(DividerItemDecoration(this@SourcesActivity, DividerItemDecoration.VERTICAL))
        }
    }

    private fun onItemClick(source: Source) {
        val intent = Intent(this@SourcesActivity, ArticlesActivity::class.java)

        intent.putExtra(SOURCE, source)
        startActivity(intent)
    }

    private fun initErrorView() {
        errorView = viewBinding.viewError

        errorView.run {
            layoutError.visibility = View.GONE
            btnRetry.setOnClickListener {
                viewModel.loadSources(viewModel.category)
            }
        }
    }

    private fun loadSources() {
        viewModel.loadSources(intent.getSerializable(CATEGORY, Category::class.java))
    }

    private fun onFailure(value: CustomResult.Failure) {
        swipeRefresh.isRefreshing = false
        swipeRefresh.visibility = View.GONE

        setViewError(value.throwable.message)
    }

    private fun setViewError(data: String?) {
        errorView.run {
            layoutError.visibility = View.VISIBLE
            tvErrorTitle.text = getString(R.string.no_result)
            tvErrorMessage.text = data
        }
    }

    private fun onLoading() {
        swipeRefresh.isRefreshing = true
        swipeRefresh.visibility = View.VISIBLE
        errorView.layoutError.visibility = View.GONE
    }

    private fun onSuccess(data: CustomResult.Success<List<Source>>) {
        swipeRefresh.isRefreshing = false
        swipeRefresh.visibility = View.VISIBLE
        errorView.layoutError.visibility = View.GONE

        showSources(data.value)
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun showSources(data: List<Source>) {
        sources.clear()
        sources.addAll(data)
        sourceAdapter.notifyDataSetChanged()
    }

    private fun resetSources() {
        viewModel.temporaryKeyword = ""
        viewModel.loadSources(viewModel.category)
    }

    override fun onRefresh() {
        resetSources()
    }
}