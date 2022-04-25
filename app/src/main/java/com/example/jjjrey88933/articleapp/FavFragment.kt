package com.example.jjjrey88933.articleapp

import android.content.Intent
import android.graphics.*
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_fav.*


private const val TAG = "FavFragment"

private val articleRecyclerViewAdapter = ArticleRecyclerViewAdapter(ArrayList())

class FavFragment : Fragment(), RecyclerViewClickListener.OnRecyclerClickListener {

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
        fav_view.addOnItemTouchListener(RecyclerViewClickListener(this.requireContext(), fav_view, this))

        val factory = InjectorUtils.provideArticleViewModelFactory(this.requireContext())
        val viewModel = ViewModelProviders.of(this, factory).get(ArticleViewModel::class.java)

        viewModel.getArticles.observe(viewLifecycleOwner, {
            newArticle -> articleRecyclerViewAdapter.loadNewData(newArticle)
        })


        // swipe

        // TODO when only 1 article deleted - make the background clear
        val deleteIcon = ContextCompat.getDrawable(this.requireContext(), R.drawable.ic_baseline_delete_24)
        val intrinsicWidth = deleteIcon?.intrinsicWidth
        val intrinsicHeight = deleteIcon?.intrinsicHeight
        val background = ColorDrawable()
        val backgroundColor = Color.parseColor("#f44336")

        val ref = this

        val simpleItemTouchCallback: ItemTouchHelper.SimpleCallback = object :
            ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder
            ): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, swipeDir: Int) {
                when (swipeDir) {
                    ItemTouchHelper.LEFT -> {
                        // get article to delete
                        val article = articleRecyclerViewAdapter.getArticle(viewHolder.adapterPosition)

                        if (article != null) {
                            viewModel.deleteArticle(article)
                        }

                        // toast message - confirm deletion and provide undo delete option
                        val mySnackbar = Snackbar.make(ref.requireView(), "Successfully deleted item!", Snackbar.LENGTH_SHORT)
                        mySnackbar.setAction(R.string.undo_string) {
                            if (article != null) {
                                viewModel.insertArticle(article)
                            }
                        }
                        mySnackbar.show()

                        // TODO after UNDO, make the screen go to the bottom of the page
                    }
                }
            }

            override fun onChildDraw(
                c: Canvas,
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                dX: Float,
                dY: Float,
                actionState: Int,
                isCurrentlyActive: Boolean
            ) {
                val itemView = viewHolder.itemView
                val itemHeight = itemView.bottom - itemView.top

                // Draw the red delete background
                background.color = backgroundColor
                background.setBounds(itemView.right + dX.toInt(), itemView.top, itemView.right, itemView.bottom)
                background.draw(c)

                val deleteIconTop = itemView.top + (itemHeight - intrinsicHeight!!) / 2
                val deleteIconMargin = (itemHeight - intrinsicHeight) / 2
                val deleteIconLeft = itemView.right - deleteIconMargin - intrinsicWidth!!
                val deleteIconRight = itemView.right - deleteIconMargin
                val deleteIconBottom = deleteIconTop + intrinsicHeight


                deleteIcon.setBounds(
                    deleteIconLeft,
                    deleteIconTop,
                    deleteIconRight,
                    deleteIconBottom
                )
                deleteIcon.draw(c)

                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
            }
        }

        val itemTouchHelper = ItemTouchHelper(simpleItemTouchCallback)
        itemTouchHelper.attachToRecyclerView(fav_view)

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

    override fun onItemShortClick(view: View, position: Int) {
        val article = articleRecyclerViewAdapter.getArticle(position)
        if (article != null) {
            val getIntent = Intent(Intent.ACTION_VIEW, Uri.parse(article.url))
            startActivity(getIntent)
        }
    }

    override fun onItemLongClick(view: View, position: Int) {
        val article = articleRecyclerViewAdapter.getArticle(position)
        activity?.supportFragmentManager?.beginTransaction()
            ?.replace(R.id.fragment_container, ArticleDetailsFragment.newInstance(article))?.addToBackStack(null)?.commit()
    }

    override fun onItemDoubleClick(view: View, position: Int) {
        // do nothing here
    }

}