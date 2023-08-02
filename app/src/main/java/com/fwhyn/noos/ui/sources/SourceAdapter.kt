package com.fwhyn.noos.ui.sources

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.fwhyn.noos.data.models.Source
import com.fwhyn.noos.databinding.ItemSourceBinding


class SourceAdapter(
    private val context: Context,
    private val dataSet: List<Source>,
    private val clickListener: (Source) -> Unit
) : RecyclerView.Adapter<SourceAdapter.ViewHolder>() {

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = ItemSourceBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)

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

        private lateinit var sourceName: TextView
        private lateinit var sourceDescription: TextView

        constructor(itemBinding: ItemSourceBinding) : this(itemBinding.root) {
            itemBinding.run {
                sourceName = tvName
                sourceDescription = tvDescription
            }
        }

        fun bind(source: Source) {
            sourceName.text = source.name
            sourceDescription.text = source.description

            view.setOnClickListener {
                clickListener(source)
            }
        }
    }
}