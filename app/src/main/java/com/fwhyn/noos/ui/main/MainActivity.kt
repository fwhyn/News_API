package com.fwhyn.noos.ui.main

import android.app.SearchManager
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.fwhyn.noos.R
import com.fwhyn.noos.data.api.RetrofitClient
import com.fwhyn.noos.data.models.Article
import com.fwhyn.noos.data.models.News
import com.fwhyn.noos.data.remote.NewsRemoteDataSource
import com.fwhyn.noos.ui.adapters.NewsAdapter
import com.fwhyn.noos.ui.helper.Utils
import com.fwhyn.noos.ui.news.NewsDetailActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivity : AppCompatActivity(), SwipeRefreshLayout.OnRefreshListener {
    private var recyclerView: RecyclerView? = null
    private var layoutManager: RecyclerView.LayoutManager? = null
    private var articles: ArrayList<Article>? = ArrayList()
    private var adapter: NewsAdapter? = null
    private val TAG = MainActivity::class.java.simpleName
    private var topHeadline: TextView? = null
    private var swipeRefreshLayout: SwipeRefreshLayout? = null
    private var errorLayout: RelativeLayout? = null
    private var errorImage: ImageView? = null
    private var errorTitle: TextView? = null
    private var errorMessage: TextView? = null
    private var btnRetry: Button? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        swipeRefreshLayout = findViewById(R.id.swipe_refresh_layout)
        swipeRefreshLayout!!.setOnRefreshListener(this)
        swipeRefreshLayout!!.setColorSchemeResources(R.color.black)
        topHeadline = findViewById(R.id.tv_source_title)
        recyclerView = findViewById(R.id.recycler_view_news)
        layoutManager = LinearLayoutManager(this@MainActivity)
        recyclerView!!.layoutManager = layoutManager
        recyclerView!!.itemAnimator = DefaultItemAnimator()
        recyclerView!!.isNestedScrollingEnabled = false
        onLoadingSwipeRefresh("")
        errorLayout = findViewById(R.id.errorLayout)
        errorImage = findViewById(R.id.errorImage)
        errorTitle = findViewById(R.id.errorTitle)
        errorMessage = findViewById(R.id.errorMessage)
        btnRetry = findViewById(R.id.btnRetry)
    }

    fun LoadJson(keyword: String) {
        errorLayout!!.visibility = View.GONE
        swipeRefreshLayout!!.isRefreshing = true
        val apiInterface = RetrofitClient().retrofit.create(
            NewsRemoteDataSource::class.java
        )
        val country = Utils.country
        val language = Utils.language
        val call: Call<News?>? = if (keyword.length > 0) {
            apiInterface.getNewsSearch(keyword, language, "publishedAt", API_KEY)
        } else {
            apiInterface.getNews(country, API_KEY)
        }
        call!!.enqueue(object : Callback<News?> {
            override fun onResponse(call: Call<News?>, response: Response<News?>) {
                if (response.isSuccessful && response.body()!!.article != null) {
                    if (articles!!.isNotEmpty()) {
                        articles!!.clear()
                    }
                    articles = response.body()!!.article?.let { ArrayList(it) }
                    adapter = NewsAdapter(this@MainActivity, articles!!.toList()) { article ->
                        initListener(article)
                    }

                    recyclerView!!.addItemDecoration(
                        DividerItemDecoration(
                            this@MainActivity,
                            DividerItemDecoration.VERTICAL
                        )
                    )
                    recyclerView!!.adapter = adapter
                    adapter!!.notifyDataSetChanged()
                    topHeadline!!.visibility = View.VISIBLE
                    swipeRefreshLayout!!.isRefreshing = false
                } else {
                    topHeadline!!.visibility = View.INVISIBLE
                    swipeRefreshLayout!!.isRefreshing = false
                    val errorCode: String
                    errorCode = when (response.code()) {
                        404 -> "404 not found"
                        500 -> "500 server broken"
                        else -> "unknown error"
                    }
                    showErrorMessage(
                        "No Result",
                        """
                            Please Try Again!
                            $errorCode
                            """.trimIndent()
                    )
                }
            }

            override fun onFailure(call: Call<News?>, t: Throwable) {
                topHeadline!!.visibility = View.INVISIBLE
                swipeRefreshLayout!!.isRefreshing = false
                showErrorMessage(
                    "Oops..",
                    """
                        Network failure, Please Try Again
                        $t
                        """.trimIndent()
                )
            }
        })
    }

    private fun initListener(article: Article) {
        val intent = Intent(this@MainActivity, NewsDetailActivity::class.java)
        intent.putExtra("ARTICLE", article)
        startActivity(intent)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_main, menu)
        val searchManager = getSystemService(SEARCH_SERVICE) as SearchManager
        val searchView = menu.findItem(R.id.action_search).actionView as SearchView?
        val searchMenuItem = menu.findItem(R.id.action_search)
        searchView!!.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = "Search Latest News..."
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                if (query.length > 2) {
                    onLoadingSwipeRefresh(query)
                } else {
                    Toast.makeText(this@MainActivity, "Type more than two letters!", Toast.LENGTH_SHORT).show()
                }
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })
        searchMenuItem.icon!!.setVisible(false, false)
        return true
    }

    override fun onRefresh() {
        LoadJson("")
    }

    private fun onLoadingSwipeRefresh(keyword: String) {
        swipeRefreshLayout!!.post { LoadJson(keyword) }
    }

    private fun showErrorMessage(title: String, message: String) {
        if (errorLayout!!.visibility == View.GONE) {
            errorLayout!!.visibility = View.VISIBLE
        }

//        errorImage.setImageResource(imageView);
        errorTitle!!.text = title
        errorMessage!!.text = message
        btnRetry!!.setOnClickListener { onLoadingSwipeRefresh("") }
    }

    companion object {
        const val API_KEY = "32faed07c15f49d3862da0a8d9940430"
    }
}