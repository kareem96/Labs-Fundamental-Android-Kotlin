package com.kareem.appusergithub.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.kareem.appusergithub.R
import com.kareem.appusergithub.model.UserItems
import com.kareem.appusergithub.view.DetailActivity

class GithubUserAdapter: RecyclerView.Adapter<GithubUserAdapter.GithubUserViewHolder>() {
    private val listGithubUser = ArrayList<UserItems>()

    fun setData(items: ArrayList<UserItems>){
        listGithubUser.clear()
        listGithubUser.addAll(items)
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GithubUserAdapter.GithubUserViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.user_item,parent, false)
        return GithubUserViewHolder(view)
    }

    override fun onBindViewHolder(holder: GithubUserAdapter.GithubUserViewHolder, position: Int) {
        val githubUser = listGithubUser[position]
        Glide.with(holder.itemView.context)
            .load(githubUser.avatar)
            .into(holder.imgPhoto)
        holder.tvName.text = githubUser.username
        holder.tvUrl.text = githubUser.url.toString()

        val mContext = holder.itemView.context
        holder.itemView.setOnClickListener{
            val moveDetailActivity = Intent(mContext, DetailActivity::class.java)
            moveDetailActivity.putExtra(DetailActivity.EXTRA_GITHUB_USER, listGithubUser[position])
            mContext.startActivity(moveDetailActivity)
        }
    }

    override fun getItemCount(): Int {
        return listGithubUser.size
    }

    inner class GithubUserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imgPhoto: ImageView = itemView.findViewById(R.id.item_image)
        var tvName: TextView = itemView.findViewById(R.id.item_name)
        var tvUrl: TextView = itemView.findViewById(R.id.item_url)
    }
}