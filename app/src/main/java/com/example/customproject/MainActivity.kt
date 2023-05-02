package com.example.customproject


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomnavigation.BottomNavigationView

import java.io.*



class MainActivity : AppCompatActivity() {



    //private lateinit var mainViewModel:MainActivityViewModel
    private var mainViewModel: MainActivityViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        //android.os.Debug.waitForDebugger()

        val btmNavView:BottomNavigationView = findViewById(R.id.bottomNavigationView)



        val mainViewModelFactory = MainActivityViewModelFactory(this)
        mainViewModel = ViewModelProvider(this, mainViewModelFactory)[MainActivityViewModel::class.java]


        /*if(mainViewModel?.chaptersList!!.isEmpty()){
            mainViewModel?.parseXml(this)
            mainViewModel?.homeFragment = HomeFragment.newInstance(mainViewModel!!.chaptersList)
            mainViewModel?.searchFragment = SearchFragment.newInstance(mainViewModel!!.chaptersList)
            mainViewModel?.currentFragment = mainViewModel!!.homeFragment

        }*/


//        Log.i("chaptersTest", mainViewModel.chaptersList[0].name)


        if(savedInstanceState == null) {
            Log.i("test15", "in")
            mainViewModel?.currentFragment?.let { setCurrentFragment(it) }
        }


        /*val itemPos = when(mainViewModel?.currentFragment) {
           mainViewModel?.homeFragment -> 0
           mainViewModel?.progressFragment -> 1
           mainViewModel?.searchFragment -> 2
           mainViewModel?.bookmarksFragment -> 3
           mainViewModel?.notesFragment -> 4
           else -> 0

        }
        mainViewModel?.currentFragment?.let { setCurrentFragment(it) }
        btmNavView.menu.getItem(itemPos).isChecked = true*/


        btmNavView.setOnItemSelectedListener {

            Log.i("test2", "inbtm")
            mainViewModel?.apply {

                currentFragment = when(it.itemId) {
                    R.id.homeMenu -> homeFragment
                    R.id.bookmarkMenu -> bookmarksFragment
                    R.id.searchMenu -> searchFragment
                    R.id.progressMenu -> progressFragment
                    R.id.notesMenu -> notesFragment
                    else -> homeFragment
                }

                setCurrentFragment(currentFragment)
            }

            true
        }

        //supportActionBar?.setDisplayHomeAsUpEnabled(true)



    }


    /*override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }*/


    override fun onResume() {
        super.onResume()

        if(mainViewModel != null)
            setCurrentFragment(mainViewModel!!.currentFragment)
    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater:MenuInflater = menuInflater
        inflater.inflate(R.menu.options_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.settingsOpt -> {
                val intent = Intent(this, SettingsActivity::class.java)
                startActivity(intent)
                true
            }else -> super.onOptionsItemSelected(item)
        }
    }




    private fun setCurrentFragment(fragment: Fragment)
    {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.flFragment, fragment)
            commit()

        }


    }




}
