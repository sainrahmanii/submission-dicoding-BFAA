package com.sainrahmani.githubuserapp.ui.detail.fragments

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sainrahmani.githubuserapp.data.ItemsItem
import com.sainrahmani.githubuserapp.databinding.ItemUserBinding

class ListFragmentAdapter(private val listUser: List<ItemsItem>) : RecyclerView.Adapter<ListFragmentAdapter.ViewHolder>() {
    class ViewHolder(val binding : ItemUserBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = listUser.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val data = listUser[position]

        Glide.with(holder.itemView.context)
            .load(data.avatarUrl)
            .into(holder.binding.ivUsers)

        holder.binding.tvUsers.text = data.login
    }
}