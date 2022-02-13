package com.example.jjjrey88933.articleapp

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_recycler.*
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
        //TODO show link
        Log.d(TAG, "onItemshortlick starts")
        val article = articleRecyclerViewAdapter.getArticle(position)
//        supportFragmentManager.beginTransaction().add(R.id.recycler_view, ArticleDetailsFragment.newInstance(article), "ArticleFrag")
        activity?.supportFragmentManager?.beginTransaction()
            ?.replace(R.id.fragment_container, ArticleDetailsFragment.newInstance(article))?.addToBackStack(null)?.commit()
    }

    override fun onItemLongClick(view: View, position: Int) {
        TODO("Not yet implemented")
    }

    companion object {
        @JvmStatic fun newInstance() =
                RecyclerFragment().apply {
                    arguments = Bundle().apply {
                    }
                }
    }
}