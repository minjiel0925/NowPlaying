package org.example.mjworkspace.myapplication.fragments.v

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.example.mjworkspace.myapplication.databinding.ListItemNowBinding
import org.example.mjworkspace.myapplication.fragments.m.model.Now


class NowAdapter : PagedListAdapter<Now, NowAdapter.ViewHolder>(NowDiffCallback()) {

    private lateinit var recyclerView: RecyclerView

    private fun isGridLayoutManager() = recyclerView.layoutManager is GridLayoutManager

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val now = getItem(position)
        now?.let {
            holder.apply {
                bind(now, isGridLayoutManager = isGridLayoutManager())
                itemView.tag = now
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ListItemNowBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }


    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        this.recyclerView = recyclerView
    }


    class ViewHolder(private val binding: ListItemNowBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Now, isGridLayoutManager: Boolean) {
            binding.apply {
                now = item
                title.visibility =
                    if (isGridLayoutManager) View.GONE else View.VISIBLE
                executePendingBindings()
            }
        }
    }

}

private class NowDiffCallback : DiffUtil.ItemCallback<Now>() {

    override fun areItemsTheSame(oldItem: Now, newItem: Now): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Now, newItem: Now): Boolean {
        return oldItem == newItem
    }
}