package com.example.jjjrey88933.articleapp

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_article_details.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

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

        val factory = InjectorUtils.provideArticleViewModelFactory(this.requireContext())
        val viewModel = ViewModelProviders.of(this, factory).get(ArticleViewModel::class.java)

        CoroutineScope(Dispatchers.IO).launch {
            Log.d(TAG, (article?.let { viewModel.findById(it.id) } != null).toString())
            if (article?.let { viewModel.findById(it.id) } != null) {
                Log.d(TAG, "Article liked")
                details_button.setBackgroundResource(R.drawable.ic_baseline_favorite_red_24)
            }
            else {
                Log.d(TAG, "Article disliked")
                details_button.setBackgroundResource(R.drawable.ic_baseline_favorite_red_border_24)
            }
        }


        if (article == null) {
            details_image.setImageResource(R.drawable.placeholder)
            details_title.context
        } else {
            Picasso.get()
                .load(article!!.imageUrl)
                .error(R.drawable.placeholder)
                .placeholder(R.drawable.placeholder)
                .into(details_image)

            details_published_at.text = "Published at: ${article!!.publishedAt.take(10)}"
            details_updated_at.text = "Updated at: ${article!!.updatedAt.take(10)}"
            details_title.text = article!!.title
            details_news_site.text = article!!.newsSite
            details_summary.text = article!!.summary
            details_url.text = article!!.url
        }

        details_title.setOnClickListener {
            val getIntent = Intent(Intent.ACTION_VIEW, Uri.parse(article?.url))
            startActivity(getIntent)
        }

        details_button.setOnClickListener {
            Log.d(TAG, "button clicked")

            CoroutineScope(Dispatchers.IO).launch {
                if (article?.let { viewModel.findById(it.id) } == null) {
                    details_button.setBackgroundResource(R.drawable.ic_baseline_favorite_red_24)

                    viewModel.insertArticle(article!!)
                } else {
                    details_button.setBackgroundResource(R.drawable.ic_baseline_favorite_red_border_24)

                    viewModel.deleteArticle(article!!)
                }
            }
        }
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