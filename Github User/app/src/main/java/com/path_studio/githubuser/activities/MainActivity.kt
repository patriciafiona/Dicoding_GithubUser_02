package com.path_studio.githubuser.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.service.autofill.UserData
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import androidx.navigation.Navigation
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.mancj.materialsearchbar.MaterialSearchBar
import com.mancj.materialsearchbar.adapter.SuggestionsAdapter
import com.path_studio.githubuser.R
import com.path_studio.githubuser.adapters.CustomSuggestionsAdapter
import com.path_studio.githubuser.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var searchBar: MaterialSearchBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Setting the Bottom Navigator
        setBottomNav()

        //set Material Search Bar
        settingSearch()
    }

    private fun settingSearch() {
        searchBar = findViewById(R.id.searchBar)
        searchBar.setMaxSuggestionCount(5)

        //enable search bar callbacks
        val inflater = getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val customSuggestionsAdapter = CustomSuggestionsAdapter(inflater, this@MainActivity)

        searchBar.setOnSearchActionListener(object : MaterialSearchBar.OnSearchActionListener {
            override fun onSearchStateChanged(enabled: Boolean) {}

            override fun onSearchConfirmed(text: CharSequence) {
                //startSearch(text.toString(), true, null, true);
            }

            override fun onButtonClicked(buttonCode: Int) {
                if (buttonCode == MaterialSearchBar.BUTTON_NAVIGATION) {
                    //opening or closing a navigation drawer
                } else if (buttonCode == MaterialSearchBar.BUTTON_BACK) {
                    searchBar.clearSuggestions()
                    searchBar.closeSearch()
                }
            }
        })
        searchBar.addTextChangeListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                searchBar.setCustomSuggestionAdapter(customSuggestionsAdapter)

                if (searchBar.text.isNotEmpty()) {
                    if (searchBar.text.length > 1) {
                        //get data that contain searchBar.text
                        //...................
                    }
                } else {
                    searchBar.clearSuggestions()
                    searchBar.hideSuggestionsList()
                }
            }

            override fun afterTextChanged(s: Editable) {}
        })
        searchBar.setSuggestionsClickListener(object :
            SuggestionsAdapter.OnItemViewClickListener {
            override fun OnItemClickListener(position: Int, v: View) {}
            override fun OnItemDeleteListener(position: Int, v: View) {}
        })

    }

    private fun setBottomNav(){
        val navView = findViewById<BottomNavigationView>(R.id.nav_view)
        val appBarConfiguration = AppBarConfiguration.Builder(
            R.id.navigation_home,
            R.id.navigation_explore,
            R.id.navigation_profile,
            R.id.navigation_settings
        )
            .build()
        val navController = Navigation.findNavController(this, R.id.nav_host_fragment)
        //NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration)
        NavigationUI.setupWithNavController(navView, navController)
    }

    override fun onResume() {
        super.onResume()
        clearSearchBar()
    }

    fun clearSearchBar(){
        searchBar = findViewById(R.id.searchBar)
        if(searchBar.isSearchOpened){
            searchBar.clearSuggestions()
            searchBar.closeSearch()
        }

    }

    fun setSearchBarVisibility(status: Int){
        val searchBar: MaterialSearchBar = this.findViewById(R.id.searchBar)
        when(status){
            0 -> {
                searchBar.visibility = View.GONE
            }
            1 -> {
                searchBar.visibility = View.VISIBLE
            }
        }
    }

}