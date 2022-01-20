package com.example.jjjrey88933.articleapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private const val TAG = "MainActivity"
internal const val ARTICLE_TRANSFER = "ARTICLE_TRANSFER"

class MainActivity : AppCompatActivity(), RecyclerViewClickListener.OnRecyclerClickListener {

    private val articleRecyclerViewAdapter = ArticleRecyclerViewAdapter(ArrayList())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recycler_view.layoutManager = LinearLayoutManager(this)
        recycler_view.adapter = articleRecyclerViewAdapter

        val apiInterface = ApiInterface.create().getArticles()

        apiInterface.enqueue(object  : Callback<List<Article>> {
            override fun onResponse(call: Call<List<Article>>, response: Response<List<Article>>) {
                if (response?.body() != null) {
                    val articleList = response.body()
                    Log.d(TAG, articleList.toString()) // works!
                    articleRecyclerViewAdapter.loadNewData(articleList)
                }
            }

            override fun onFailure(call: Call<List<Article>>, t: Throwable) {
                Log.d(TAG, t.toString())
            }
        })

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onItemClick(view: View, position: Int) {
        // TODO
        // TODO 2 put recycler view into fragment

//        supportFragmentManager.beginTransaction().add(R.id.recycler_view, ArticleDetailsFragment.newInstance(), "ArticleFrag")
    }
}