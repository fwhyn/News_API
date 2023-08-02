package com.fwhyn.noos.ui.articles

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.fwhyn.noos.data.helper.Utils
import com.fwhyn.noos.data.models.Article
import com.fwhyn.noos.databinding.ItemArticleBinding


class ArticleAdapter(
    private val context: Context,
    private val dataSet: List<Article>,
    private val clickListener: (Article) -> Unit
) : RecyclerView.Adapter<ArticleAdapter.ViewHolder>() {

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = ItemArticleBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)

        return ViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        viewHolder.bind(dataSet[position])
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = dataSet.size

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    inner class ViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        private lateinit var source: TextView
        private lateinit var author: TextView
        private lateinit var title: TextView
        private lateinit var publishedAt: TextView
        private lateinit var time: TextView
        private lateinit var thumbnail: ImageView

        constructor(itemBinding: ItemArticleBinding) : this(itemBinding.root) {
            itemBinding.run {
                source = tvSource
                author = tvAuthor
                title = tvTitle
                publishedAt = tvPublishedAt
                time = tvTime
                thumbnail = imgThumbnail
            }
        }

        fun bind(article: Article) {
            source.text = article.source?.name
            author.text = article.author
            title.text = article.title
            publishedAt.text = article.publishedAt?.let { Utils.getNewDateFormat(it) }
            time.text = ""

            val options = RequestOptions()
                .placeholder(Utils.randomDrawableColor)
                .error(Utils.randomDrawableColor)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .override(100, 100)
                .centerCrop()

            Glide.with(context)
                .load(article.urlToImage)
                .apply(options)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(thumbnail)

            view.setOnClickListener {
                clickListener(article)
            }
        }


    }
}