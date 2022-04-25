package com.example.jjjrey88933.articleapp

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import java.lang.IllegalArgumentException

private const val TAG = "ArtRecyclerViewAdapt"

class ArticleViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    var image: ImageView = view.findViewById(R.id.item_image)
    var title: TextView = view.findViewById(R.id.item_title)
    var newsSite: TextView = view.findViewById(R.id.item_news_site)
    var date: TextView = view.findViewById(R.id.item_date)
    var cardView: CardView = view.findViewById(R.id.article_cardView)
}

class FavViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    var image: ImageView = view.findViewById(R.id.fav_image)
    var date: TextView = view.findViewById(R.id.fav_date)
    var title: TextView = view.findViewById(R.id.fav_title)
}

class ArticleRecyclerViewAdapter(private var articleList: List<Article>) : RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (parent.id) {
            R.id.recycler_view -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.article_item, parent, false)
                ArticleViewHolder(view)
            }
            R.id.fav_view -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.favourite_item, parent, false)
                FavViewHolder(view)
            }
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ArticleViewHolder -> {
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

                    holder.cardView.animation =
                        AnimationUtils.loadAnimation(holder.itemView.context, R.anim.article_recycler_anim)
                }
            }
            is FavViewHolder -> {
                Log.d(TAG, articleList.toString())
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
                    holder.date.text = articleItem.publishedAt.take(10) // first 10 chars needed
                }
            }
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