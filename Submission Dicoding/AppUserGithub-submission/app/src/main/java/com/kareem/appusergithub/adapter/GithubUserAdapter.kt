package com.kareem.appusergithub.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.kareem.appusergithub.R
import com.kareem.appusergithub.data.model.UserItems
import com.kareem.appusergithub.databinding.UserItemBinding
import com.kareem.appusergithub.view.DetailActivity
import com.kareem.appusergithub.view.DetailActivity.Companion.EXTRA_GITHUB_USER

class GithubUserAdapter: RecyclerView.Adapter<GithubUserAdapter.GithubUserViewHolder>() {
    private val listGithubUser = ArrayList<UserItems>()

    fun setData(items: List<UserItems>){
        listGithubUser.apply {
            clear()
            addAll(items)
        }
        /*listGithubUser.clear()
        listGithubUser.addAll(items)
        notifyDataSetChanged()*/
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GithubUserViewHolder {
        val view = UserItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GithubUserViewHolder(view)
    }

    override fun onBindViewHolder(holder: GithubUserAdapter.GithubUserViewHolder, position: Int) {
        holder.bind(listGithubUser[position])
    }

    override fun getItemCount(): Int = listGithubUser.size


    inner class GithubUserViewHolder(private val view: UserItemBinding) : RecyclerView.ViewHolder(view.root) {
        fun  bind(user: UserItems){
            view.apply {
                itemName.text = user.username
            }
            Glide.with(itemView.context)
                .load(user.avatar)
                .apply(RequestOptions.circleCropTransform())
                .into(view.itemImage)

            itemView.setOnClickListener{
                val moveDetailActivity = Intent(itemView.context, DetailActivity::class.java)
                moveDetailActivity.putExtra(EXTRA_GITHUB_USER, user.username)
                itemView.context.startActivity(moveDetailActivity)
            }
        }
    }
}