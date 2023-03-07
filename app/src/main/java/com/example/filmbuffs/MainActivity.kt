package com.example.filmbuffs


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.SearchView
import androidx.appcompat.widget.Toolbar
import com.example.filmbuffs.fragments.FavoriteMoviesFragment
import com.example.filmbuffs.fragments.MainFragment
import com.google.android.material.bottomnavigation.BottomNavigationView


class MainActivity : AppCompatActivity(), SearchView.OnQueryTextListener {
    private val TAG = "Main Activity"
    private var mainFragment = MainFragment()
    private var favoriteMoviesFragment = FavoriteMoviesFragment()
    private lateinit var bottomNavView: BottomNavigationView
    private lateinit var toolbar: Toolbar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setUpToolbar()
        setUpBottomNav()
    }

    private fun setUpBottomNav() {
        bottomNavView = findViewById<BottomNavigationView>(R.id.bottomNav)
        bottomNavView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home_page -> {
                    supportFragmentManager.beginTransaction().apply {
                        replace(R.id.fragmentContainer, mainFragment)
                        commit()
                    }
                }
                R.id.favorites_page -> {
                    supportFragmentManager.beginTransaction().apply {
                        replace(R.id.fragmentContainer, favoriteMoviesFragment)
                        commit()
                    }
                }
            }
            true

        }
    }

    private fun setUpToolbar() {
        supportFragmentManager
            .beginTransaction()
            .add(R.id.fragmentContainer, mainFragment)
            .commit()
        toolbar = findViewById<Toolbar>(R.id.toolbar)
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
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true

            }
            R.id.action_search -> {
                // handling of the search button click
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onBackPressed() {
        val searchView = findViewById<SearchView>(R.id.action_search)
        if (mainFragment.isVisible) {
            searchView.clearFocus()
            searchView.setQuery("", false)
            mainFragment.showDefaultResults()

        } else {
            super.onBackPressed()
        }
    }
}