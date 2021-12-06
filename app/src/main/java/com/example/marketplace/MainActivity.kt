package com.example.marketplace

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.onNavDestinationSelected
import com.example.marketplace.fragments.TimelineFragment
import com.example.marketplace.fragments.MyFaresFragment
import com.example.marketplace.fragments.MyMarketFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.bottom_navigation
import kotlinx.android.synthetic.main.fragment_timeline.*

class MainActivity : AppCompatActivity() {

    private lateinit var navController : NavController

    private val myMarketFragment = MyMarketFragment()
    private val timelineFragment = TimelineFragment()
    private val myFaresFragment = MyFaresFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navController = findNavController(R.id.nav_host_fragment)
        // Up navigation
        NavigationUI.setupActionBarWithNavController(this, navController)

        var navView : BottomNavigationView = findViewById(R.id.bottom_navigation)

        navView.visibility = View.GONE

        bottom_navigation.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.timeline -> {
                    replaceFragment(timelineFragment)
                }
                R.id.myMarket -> {
                    replaceFragment(myMarketFragment)
                }
                R.id.MyFares ->{
                    replaceFragment(myFaresFragment)
                }
            }
            true
        }
    }

    private fun replaceFragment(fragment : Fragment){
        if( fragment != null){
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.nav_host_fragment, fragment)
            transaction.commit()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}