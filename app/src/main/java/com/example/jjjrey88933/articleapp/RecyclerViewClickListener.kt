package com.example.jjjrey88933.articleapp

import android.content.Context
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import androidx.recyclerview.widget.RecyclerView


private const val TAG = "RecyclerViewClickListener"

class RecyclerViewClickListener(context: Context, recyclerView: RecyclerView, private val listener: OnRecyclerClickListener)
    : RecyclerView.SimpleOnItemTouchListener() {

    interface OnRecyclerClickListener {
        fun onItemShortClick(view: View, position: Int)
        fun onItemLongClick(view: View, position: Int)
    }

    private val gestureDetector = GestureDetector(context, object  : GestureDetector.SimpleOnGestureListener() {
        override fun onSingleTapUp(e: MotionEvent?): Boolean {
            if (e != null) {
                val childView = recyclerView.findChildViewUnder(e.x, e.y)
                if (childView != null)
                    listener.onItemShortClick(childView, recyclerView.getChildAdapterPosition(childView))
            }
            return true;
        }
    })

    override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {
        return gestureDetector.onTouchEvent(e)
    }
}