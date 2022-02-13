package com.example.jjjrey88933.articleapp

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_article_details.*

private const val TAG = "ArticleDetailsFragment"
private const val ARG_ARTICLE = "article"

class ArticleDetailsFragment : Fragment() {
    private var article: Article? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(TAG, "onCreate starts")
        super.onCreate(savedInstanceState)
        arguments?.let {
            article = it.getParcelable(ARG_ARTICLE)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        Log.d(TAG, "onCreateView starts")
        return inflater.inflate(R.layout.fragment_article_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        if (article == null) {
            details_image.setImageResource(R.drawable.placeholder)
            details_title.context
        } else {
            Picasso.get()
                .load(article!!.imageUrl)
                .error(R.drawable.placeholder)
                .placeholder(R.drawable.placeholder)
                .into(details_image)

            details_published_at.text = article!!.publishedAt.take(10)
            details_updated_at.text = article!!.updatedAt.take(10)
            details_title.text = article!!.title
            details_news_site.text = article!!.newsSite
            details_summary.text = article!!.summary
        }
//        super.onViewCreated(view, savedInstanceState)
    }

    companion object {
        @JvmStatic
        fun newInstance(art: Article?) =
            ArticleDetailsFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(ARG_ARTICLE, art)
                }
                Log.d(TAG, "fragment new instance")
            }

    }
}