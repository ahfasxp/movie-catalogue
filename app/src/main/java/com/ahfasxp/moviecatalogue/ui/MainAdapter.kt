package com.ahfasxp.moviecatalogue.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ahfasxp.moviecatalogue.R
import com.ahfasxp.moviecatalogue.data.source.local.entity.MainEntity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.list_items.view.*

class MainAdapter internal constructor() :
    PagedListAdapter<MainEntity, MainAdapter.MainViewHolder>(DIFF_CALLBACK) {
    private var onItemClickCallback: OnItemClickCallback? = null

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<MainEntity>() {
            override fun areItemsTheSame(oldItem: MainEntity, newItem: MainEntity): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: MainEntity, newItem: MainEntity): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MainViewHolder {
        val mView = LayoutInflater.from(parent.context).inflate(R.layout.list_items, parent, false)
        return MainViewHolder(mView)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        val main = getItem(position)
        if (main != null) {
            holder.bind(main)
        }
    }

    inner class MainViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(main: MainEntity) {
            with(itemView) {
                Glide.with(itemView.context)
                    .load(main.poster_path)
                    .apply(
                        RequestOptions().override(350, 550).placeholder(R.drawable.ic_loading)
                            .error(R.drawable.ic_error)
                    )
                    .into(img_poster)
                tv_title.text = main.title

                itemView.setOnClickListener { onItemClickCallback?.onItemClicked(main) }
            }
        }
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: MainEntity)
    }
}