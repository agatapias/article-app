package com.example.jjjrey88933.articleapp

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

private const val TAG = "ArtRecyclerViewAdapt"

class ArticleViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    var image: ImageView = view.findViewById(R.id.item_image)
    var title: TextView = view.findViewById(R.id.item_title)
    var newsSite: TextView = view.findViewById(R.id.item_news_site)
    var date: TextView = view.findViewById(R.id.item_date)
}

class ArticleRecyclerViewAdapter(private var articleList: List<Article>) : RecyclerView.Adapter<ArticleViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.article_item, parent, false)
        return ArticleViewHolder(view)
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        Log.d(TAG, "onBindViewHolder starts")
        if (articleList.isEmpty()) {
            holder.image.setImageResource(R.drawable.placeholder)
            holder.title.context
        } else {
            val articleItem = articleList[position]
            Picasso.get()
                .load(articleItem.imageUrl)
                .error(R.drawable.placeholder)
                .placeholder(R.drawable.placeholder)
                .into(holder.image)

            holder.title.text = articleItem.title
            holder.newsSite.text = articleItem.newsSite
            holder.date.text = articleItem.publishedAt.take(10) // first 10 chars needed
        }
    }

    override fun getItemCount(): Int {
        return if (articleList.isNotEmpty()) articleList.size else 1
    }

    fun loadNewData(newArticles: List<Article>?) {
        if (newArticles != null) {
            articleList = newArticles
        }
        notifyDataSetChanged()
    }

    fun getArticle(position: Int) : Article? {
        return if (articleList.isNotEmpty()) articleList[position] else null
    }
}