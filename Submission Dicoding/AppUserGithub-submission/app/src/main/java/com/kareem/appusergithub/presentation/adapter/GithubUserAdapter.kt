package com.kareem.appusergithub.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.kareem.appusergithub.data.local.room.UserItems
import com.kareem.appusergithub.databinding.UserItemBinding

class GithubUserAdapter(private val listUser: ArrayList<UserItems>): RecyclerView.Adapter<GithubUserAdapter.GithubUserViewHolder>() {


    private lateinit var onItemClickCallback: OnItemClickCallback

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GithubUserViewHolder {
        val view = UserItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GithubUserViewHolder(view)
    }

    override fun onBindViewHolder(holder: GithubUserAdapter.GithubUserViewHolder, position: Int) {
        val user = listUser[position]
        Glide.with(holder.itemView.context)
            .load(user.avatarUrl)
            .apply(RequestOptions.circleCropTransform())
            .into(holder.view.itemImage)
        holder.view.itemName.text = user.login
        holder.itemView.setOnClickListener{
            onItemClickCallback.onItemClicked(listUser[holder.adapterPosition])
        }
    }

    override fun getItemCount(): Int = listUser.size
    inner class GithubUserViewHolder(var view: UserItemBinding) : RecyclerView.ViewHolder(view.root)

    interface OnItemClickCallback{
        fun onItemClicked(data: UserItems)
    }
    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback){
        this.onItemClickCallback = onItemClickCallback
    }

}