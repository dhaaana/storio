package com.dhana.storio.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dhana.storio.data.remote.response.Story
import com.dhana.storio.databinding.ItemStoryBinding

//class StoryListAdapter(private val storyList: List<Story>) :
//    RecyclerView.Adapter<StoryListAdapter.ViewHolder>() {
//
//    private lateinit var onItemClickCallback: OnItemClickCallback
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
//        val binding =
//            ItemStoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
//        return ViewHolder(binding)
//    }
//
//    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        val story = storyList[position]
//        holder.binding.tvItemName.text = story.name
//        Glide.with(holder.itemView.context).load(story.photoUrl).into(holder.binding.ivItemPhoto)
//        holder.itemView.setOnClickListener {
//            onItemClickCallback.onItemClicked(story)
//        }
//    }
//
//    override fun getItemCount(): Int = storyList.size
//
//    class ViewHolder(var binding: ItemStoryBinding) :
//        RecyclerView.ViewHolder(binding.root)
//
//    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
//        this.onItemClickCallback = onItemClickCallback
//    }
//
//    interface OnItemClickCallback {
//        fun onItemClicked(data: Story)
//    }
//}

class StoryListAdapter:
    PagingDataAdapter<Story, StoryListAdapter.ViewHolder>(DIFF_CALLBACK)  {

    private lateinit var onItemClickCallback: OnItemClickCallback

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemStoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val story = getItem(position)
        if (story != null) {
            holder.binding.tvItemName.text = story.name
            Glide.with(holder.itemView.context).load(story.photoUrl).into(holder.binding.ivItemPhoto)
            holder.itemView.setOnClickListener {
                onItemClickCallback.onItemClicked(story)
            }
        }

    }

    class ViewHolder(var binding: ItemStoryBinding) :
        RecyclerView.ViewHolder(binding.root)

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: Story)
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Story>() {
            override fun areItemsTheSame(oldItem: Story, newItem: Story): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Story, newItem: Story): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }
}