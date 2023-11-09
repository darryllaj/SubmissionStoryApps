package com.dicoding.submissionstoryapps.story

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.submissionstoryapps.Response.ListStoryItem
import com.dicoding.submissionstoryapps.databinding.ItemStoryBinding

class StoryAdapter(private val listStory: List<ListStoryItem>): ListAdapter<ListStoryItem,StoryAdapter.UserViewHolder>(DIFF_CALLBACK) {

    private var onItemClickCallback: OnItemClickCallback? = null


    inner class UserViewHolder(private val binding : ItemStoryBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(storyResponse: ListStoryItem){
            binding.root.setOnClickListener {
                onItemClickCallback?.onItemClicked(storyResponse)
            }
            binding.apply {
                Glide.with(binding.root)
                    .load(storyResponse.photoUrl).into(binding.imageView)
                binding.tvJudul.text = storyResponse.name
                binding.tvPublikasi.text = storyResponse.createdAt
                binding.tvIsidesc.text = storyResponse.description
            }
        }
    }
    fun setOnItemCallback(onItemClickCallback: OnItemClickCallback){
        this.onItemClickCallback = onItemClickCallback
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val binding = ItemStoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return listStory.size
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val story = listStory[position]
        holder.bind(story)
    }
    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ListStoryItem>() {
            override fun areItemsTheSame(oldItem: ListStoryItem, newItem: ListStoryItem): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: ListStoryItem,
                newItem: ListStoryItem
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
    interface OnItemClickCallback{
        fun onItemClicked(data: ListStoryItem)
    }
}