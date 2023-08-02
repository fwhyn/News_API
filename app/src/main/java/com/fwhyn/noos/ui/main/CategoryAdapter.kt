package com.fwhyn.noos.ui.main

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.fwhyn.noos.data.models.Category
import com.fwhyn.noos.databinding.ItemCategoryBinding


class CategoryAdapter(
    private val context: Context,
    private val dataSet: List<Category>,
    private val clickListener: (Category) -> Unit
) : RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = ItemCategoryBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)

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
        private lateinit var categoryName: TextView
        private lateinit var thumbnail: ImageView

        constructor(itemBinding: ItemCategoryBinding) : this(itemBinding.root) {
            itemBinding.run {
                categoryName = tvCategory
                thumbnail = imgThumbnail
            }
        }

        fun bind(category: Category) {
            categoryName.text = category.name
            thumbnail.setBackgroundColor(category.colorCode)

            view.setOnClickListener {
                clickListener(category)
            }
        }
    }
}