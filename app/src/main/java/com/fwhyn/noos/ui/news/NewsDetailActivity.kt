package com.fwhyn.noos.ui.news

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.activity.viewModels
import com.fwhyn.noos.R
import com.fwhyn.noos.basecomponent.baseclass.BaseActivityBinding
import com.fwhyn.noos.basecomponent.baseclass.ErrorView
import com.fwhyn.noos.basecomponent.baseclass.ProgressBarView
import com.fwhyn.noos.data.helper.Utils
import com.fwhyn.noos.data.models.Article
import com.fwhyn.noos.databinding.ActivityNewsDetailBinding
import com.fwhyn.noos.databinding.ToolbarCustomBinding
import com.fwhyn.noos.ui.helper.Constants
import com.fwhyn.noos.ui.helper.showToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewsDetailActivity : BaseActivityBinding<ActivityNewsDetailBinding>() {

    private lateinit var toolbar: ToolbarCustomBinding
    private lateinit var progressBar: ProgressBarView
    private lateinit var webView: WebView
    private lateinit var errorView: ErrorView

    private val viewModel: NewsDetailViewModel by viewModels()

    override fun onBinding(): ActivityNewsDetailBinding {
        return ActivityNewsDetailBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        init()
    }

    private fun init() {
        initData()
        initView()
        initObserver()
    }

    private fun initData() {
        viewModel.article.value = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getSerializableExtra(Constants.ARTICLE, Article::class.java)
        } else {
            intent.getSerializableExtra(Constants.ARTICLE) as? Article
        }
    }

    private fun initView() {
        initAppbar()
        iniProgressBar()
        initWebView()
    }

    private fun initAppbar() {
        toolbar = viewBinding.toolbarCustom

        setSupportActionBar(toolbar.toolbar)
        supportActionBar?.title = ""
        supportActionBar?.subtitle = ""
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun iniProgressBar() {
        progressBar = ProgressBarView(viewBinding.viewProgressBar)
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun initWebView() {
        webView = viewBinding.webViewNewsContent
        webView.run {
            settings.loadsImagesAutomatically = true
            settings.javaScriptEnabled = true
            settings.domStorageEnabled = true

            webViewClient = object : WebViewClient() {
                override fun onReceivedError(view: WebView?, request: WebResourceRequest?, error: WebResourceError?) {
                    super.onReceivedError(view, request, error)

                    progressBar.showLayout(false)
                }
            }

            webChromeClient= object : WebChromeClient() {
                override fun onProgressChanged(view: WebView?, newProgress: Int) {
                    super.onProgressChanged(view, newProgress)

                    progressBar.showLayout(newProgress != 100)
                }
            }
        }
    }

    private fun initErrorView() {
        errorView = ErrorView(viewBinding.viewError)
        errorView.setButton({
            loadArticle(viewModel.article.value)

        })
    }

    private fun initObserver() {
        viewModel.article.observe(this) {
            updateToolbar(it)
            updateHeader(it)
            loadArticle(it)
        }
    }

    private fun updateToolbar(article: Article) {
        toolbar.tvAppbarTitle.text = article.source?.name
        toolbar.tvAppbarSubtitle.text = article.url
    }

    private fun updateHeader(article: Article) {
        viewBinding.run {
            tvTitle.text = article.title
            tvAuthor.text = article.author
            tvPublishedAt.text = article.publishedAt?.let { date -> Utils.getNewDateFormat(date) }
            tvTime.text = ""
        }
    }

    private fun loadArticle(article: Article?) {
        if (article?.url != null) {
            webView.loadUrl(article.url!!)
        } else {
            showToast("Error Load Article")
        }
    }

    private fun openByWeb() {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(viewModel.article.value?.url)
        startActivity(intent)
    }

    private fun shareUrl() {
        val article = viewModel.article.value

        try {
            val intent = Intent(Intent.ACTION_SEND)

            intent.type = "text/plan"
            intent.putExtra(Intent.EXTRA_SUBJECT, article?.source?.name)

            val body = "${article?.title}.\n${article?.url}\nShared from Noos App\n"
            intent.putExtra(Intent.EXTRA_TEXT, body)

            startActivity(Intent.createChooser(intent, "Share with:"))
        } catch (e: Exception) {
            Toast.makeText(this, "Sorry, \nCannot be shared", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_news, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.view_web -> openByWeb()
            R.id.share -> shareUrl()
        }

        return super.onOptionsItemSelected(item)
    }
}