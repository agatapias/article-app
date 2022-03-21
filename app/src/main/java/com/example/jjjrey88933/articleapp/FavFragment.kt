package com.example.jjjrey88933.articleapp

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_fav.*
import kotlin.collections.ArrayList


private const val TAG = "FavFragment"

private val articleRecyclerViewAdapter = ArticleRecyclerViewAdapter(ArrayList())

class FavFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_fav, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Log.d(TAG, "onViewCreated starts")

        fav_view.layoutManager = LinearLayoutManager(activity)
        fav_view.adapter = articleRecyclerViewAdapter

        val factory = InjectorUtils.provideArticleViewModelFactory(this.requireContext())
        val viewModel = ViewModelProviders.of(this, factory).get(ArticleViewModel::class.java)

        viewModel.getArticles.observe(viewLifecycleOwner, {
            newArticle -> articleRecyclerViewAdapter.loadNewData(newArticle)
        })

        super.onViewCreated(view, savedInstanceState)
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            FavFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}