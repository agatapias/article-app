 package com.example.jjjrey88933.articleapp

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(TAG, "onCreate starts")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Log.d(TAG, "onCreate: before transaction")
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_favourites ->  {
                val currFrag = this.supportFragmentManager.findFragmentByTag("FAV_FRAGMENT")
                // check if fragment already exists
                if (currFrag == null) this.supportFragmentManager?.beginTransaction()
                    ?.replace(R.id.fragment_container, FavFragment.newInstance(), "FAV_FRAGMENT")?.addToBackStack(null)?.commit()
            }
        }
        return super.onOptionsItemSelected(item)
    }

}