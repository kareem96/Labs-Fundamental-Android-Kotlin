package com.kareemdev.latihanrepositorydi.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.kareemdev.latihanrepositorydi.R
import com.kareemdev.latihanrepositorydi.data.local.entity.NewsEntity
import com.kareemdev.latihanrepositorydi.databinding.ItemNewsBinding
import com.kareemdev.latihanrepositorydi.utils.DateFormatter

class NewsAdapter(private val onBookmarkClick:(NewsEntity) -> Unit) : ListAdapter<NewsEntity, NewsAdapter.MyViewHolder>(DIFF_CALLBACK) {
    companion object{
        val DIFF_CALLBACK: DiffUtil.ItemCallback<NewsEntity> = object : DiffUtil.ItemCallback<NewsEntity>(){
            override fun areItemsTheSame(oldItem: NewsEntity, newItem: NewsEntity): Boolean {
                return oldItem.title == newItem.title
            }
            @SuppressLint("DiffUtilEquals")
            override fun areContentsTheSame(oldItem: NewsEntity, newItem: NewsEntity): Boolean {
                return oldItem == newItem
            }
        }
    }
    class MyViewHolder(val binding: ItemNewsBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(news: NewsEntity){
            binding.tvItemTitle.text = news.title
            binding.tvItemPublishedDate.text = DateFormatter.formatDate(news.publishedAt)
            Glide.with(itemView.context)
                .load(news.urlToImage)
                .apply(RequestOptions.placeholderOf(R.drawable.ic_loading).error(R.drawable.ic_error))
                .into(binding.imgPoster)
            itemView.setOnClickListener {
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse(news.url)
                itemView.context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemNewsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return  MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val news = getItem(position)
        holder.bind(news)

        //
        val ivBookmark = holder.binding.ivBookmark
        if(news.isBookmarked){
            ivBookmark.setImageDrawable(ContextCompat.getDrawable(ivBookmark.context, R.drawable.ic_bookmarked))
        }else{
            ivBookmark.setImageDrawable(ContextCompat.getDrawable(ivBookmark.context, R.drawable.ic_bookmark))
        }
        ivBookmark.setOnClickListener {
            onBookmarkClick(news)
        }
    }
}