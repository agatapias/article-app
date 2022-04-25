package com.example.jjjrey88933.articleapp

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.article_item.*
import kotlinx.android.synthetic.main.fragment_recycler.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.Dispatcher
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


private const val TAG = "RecyclerFragment"

private val articleRecyclerViewAdapter = ArticleRecyclerViewAdapter(ArrayList())

class RecyclerFragment : Fragment(), RecyclerViewClickListener.OnRecyclerClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(TAG, "onCreate starts")
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_recycler, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Log.d(TAG, "onViewCreated starts")

        recycler_view.layoutManager = LinearLayoutManager(activity)
        recycler_view.adapter = articleRecyclerViewAdapter
        recycler_view.addOnItemTouchListener(RecyclerViewClickListener(this.requireContext(), recycler_view, this))

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

        super.onViewCreated(view, savedInstanceState)
    }

    override fun onItemShortClick(view: View, position: Int) {
        Log.d(TAG, "onItemShortClick called")
        val article = articleRecyclerViewAdapter.getArticle(position)
        val getIntent = Intent(Intent.ACTION_VIEW, Uri.parse(article?.url))
        startActivity(getIntent)
    }

    override fun onItemLongClick(view: View, position: Int) {
        Log.d(TAG, "onItemLongClick called")
        val article = articleRecyclerViewAdapter.getArticle(position)
        activity?.supportFragmentManager?.beginTransaction()
            ?.replace(R.id.fragment_container, ArticleDetailsFragment.newInstance(article))?.addToBackStack(null)?.commit()
    }

    override fun onItemDoubleClick(view: View, position: Int) {
        Log.d(TAG, "onItemDoubleClick called")
        val article = articleRecyclerViewAdapter.getArticle(position)
        val ref = this
        CoroutineScope(Dispatchers.IO).launch {
            val db = AppDatabase.getInstance(ref.requireContext())

            ref.activity?.runOnUiThread {
                if (article != null) {
                    Log.d(TAG, "onItemDoubleClick: added to database")
                    CoroutineScope(Dispatchers.IO).launch {
                        db.articleDao().insertArticles(article)
                    }
                }
            }
        }
    }

    companion object {
        @JvmStatic fun newInstance() =
                RecyclerFragment().apply {
                    arguments = Bundle().apply {
                    }
                }
    }
}