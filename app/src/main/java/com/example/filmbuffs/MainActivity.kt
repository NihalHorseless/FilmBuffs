package com.example.filmbuffs

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.SearchView
import androidx.appcompat.widget.Toolbar
import com.example.filmbuffs.fragments.MainFragment

class MainActivity : AppCompatActivity(), SearchView.OnQueryTextListener {
    private val TAG = "Main Activity"
    private lateinit var mainFragment : MainFragment
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mainFragment = MainFragment()
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragmentContainer, mainFragment)
            .commit()
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        val searchItem = menu?.findItem(R.id.action_search)
        val searchView = searchItem?.actionView as SearchView
        searchView.setOnQueryTextListener(this)
        return true
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        if (query != null && query.isNotBlank()) {
            mainFragment.search(query)
            return true
        }
        return false
    }


    override fun onQueryTextChange(newText: String): Boolean {

        return false
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
            R.id.action_search -> {
                // handling of the search button click
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }
}