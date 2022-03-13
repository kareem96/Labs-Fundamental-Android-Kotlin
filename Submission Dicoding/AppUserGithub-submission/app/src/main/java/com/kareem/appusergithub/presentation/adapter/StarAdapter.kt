package com.kareem.appusergithub.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.kareem.appusergithub.data.local.entity.UserEntity
import com.kareem.appusergithub.databinding.UserItemBinding

class StarAdapter(private val listStar: List<UserEntity>) :
    RecyclerView.Adapter<StarAdapter.ViewHolder>() {

    private lateinit var onItemCallBack: OnItemClickCallback


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = UserItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: StarAdapter.ViewHolder, position: Int) {
        val star = listStar[position]
        Glide.with(holder.itemView.context)
            .load(star.avatar_url)
            .circleCrop()
            .into(holder.binding.itemImage)
        holder.binding.itemName.text = star.login
        holder.itemView.setOnClickListener {
            onItemCallBack.onItemClicked(listStar[holder.adapterPosition])
        }
    }

    inner class ViewHolder(val binding: UserItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun getItemCount(): Int = listStar.size

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemCallBack = onItemClickCallback
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: UserEntity)
    }
}