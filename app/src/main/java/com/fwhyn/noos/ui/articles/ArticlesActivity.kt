package com.fwhyn.noos.ui.articles

import android.annotation.SuppressLint
import android.app.SearchManager
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.fwhyn.noos.R
import com.fwhyn.noos.basecomponent.baseclass.BaseActivityBinding
import com.fwhyn.noos.data.helper.getSerializable
import com.fwhyn.noos.data.models.Article
import com.fwhyn.noos.data.models.Source
import com.fwhyn.noos.databinding.ActivityMainBinding
import com.fwhyn.noos.databinding.ViewErrorBinding
import com.fwhyn.noos.ui.adapters.NewsAdapter
import com.fwhyn.noos.ui.helper.Constants.SOURCE
import com.fwhyn.noos.ui.helper.CustomResult
import com.fwhyn.noos.ui.helper.showToast
import com.fwhyn.noos.ui.news.NewsDetailActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ArticlesActivity : BaseActivityBinding<ActivityMainBinding>(), SwipeRefreshLayout.OnRefreshListener {

    private lateinit var swipeRefresh: SwipeRefreshLayout
    private lateinit var errorView: ViewErrorBinding

    private lateinit var articlesView: RecyclerView
    private lateinit var newsAdapter: NewsAdapter
    private val articles = ArrayList<Article>()

    private val viewModel: ArticlesViewModel by viewModels()
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
            loadArticles()
        }
    }

    private fun initObserver() {
        viewModel.articles.observe(this) {
            when (it) {
                is CustomResult.Failure -> onFailure(it)
                CustomResult.Loading -> onLoading()
                is CustomResult.Success -> onSuccess(it)
            }
        }
    }

    private fun initView() {
        initSwipeRefresh()
        initArticlesView()
        initErrorView()
    }

    private fun initSwipeRefresh() {
        swipeRefresh = viewBinding.layoutSwipeRefresh

        swipeRefresh.run {
            setOnRefreshListener(this@ArticlesActivity)
            setColorSchemeResources(R.color.gray)
        }
    }

    private fun initArticlesView() {
        articlesView = viewBinding.recyclerViewNews
        newsAdapter = NewsAdapter(this@ArticlesActivity, articles) { article ->
            onItemClick(article)
        }

        articlesView.run {
            layoutManager = LinearLayoutManager(this@ArticlesActivity)
            itemAnimator = DefaultItemAnimator()
            isNestedScrollingEnabled = false
            addItemDecoration(DividerItemDecoration(this@ArticlesActivity, DividerItemDecoration.VERTICAL))
            adapter = newsAdapter
        }
    }

    private fun onItemClick(article: Article) {
        val intent = Intent(this@ArticlesActivity, NewsDetailActivity::class.java)
        intent.putExtra("ARTICLE", article)
        startActivity(intent)
    }

    private fun initErrorView() {
        errorView = viewBinding.viewError

        errorView.run {
            layoutError.visibility = View.GONE
            btnRetry.setOnClickListener {
                viewModel.run {
                    loadArticles(source, temporaryKeyword)
                }
            }
        }
    }

    private fun loadArticles() {
        viewModel.run {
            loadArticles(intent.getSerializable(SOURCE, Source::class.java), temporaryKeyword)
        }
    }

    private fun onFailure(value: CustomResult.Failure) {
        swipeRefresh.isRefreshing = false

        value.throwable.message?.let { setViewError(it) }
    }

    private fun setViewError(data: String) {
        errorView.run {
            layoutError.visibility = View.VISIBLE
            tvErrorTitle.text = getString(R.string.no_result)
            tvErrorMessage.text = data
        }
    }

    private fun onLoading() {
        swipeRefresh.isRefreshing = true
        errorView.layoutError.visibility = View.GONE
    }

    private fun onSuccess(data: CustomResult.Success<List<Article>>) {
        swipeRefresh.isRefreshing = false
        errorView.layoutError.visibility = View.GONE

        showArticles(data.value)
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun showArticles(data: List<Article>) {
        articles.clear()
        articles.addAll(data)
        newsAdapter.notifyDataSetChanged()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_main, menu)
        val searchManager = getSystemService(SEARCH_SERVICE) as SearchManager
        val searchView = menu.findItem(R.id.action_search).actionView as SearchView?
        val searchMenuItem = menu.findItem(R.id.action_search)
        searchView!!.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = "Search News"
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                if (query.length > 2) {
                    viewModel.run {
                        temporaryKeyword = query
                        loadArticles(source, temporaryKeyword)
                    }

                } else {
                    showToast("Please type more than two letters.")
                }

                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                viewModel.temporaryKeyword = newText

                return false
            }
        })
        searchMenuItem.icon!!.setVisible(false, false)
        return true
    }

    override fun onRefresh() {
        viewModel.run {
            temporaryKeyword = ""
            loadArticles(source, temporaryKeyword)
        }
    }
}