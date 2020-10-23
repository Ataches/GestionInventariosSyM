package com.example.stockmanagementsym

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.stockmanagementsym.presentation.view.FragmentData
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.layout_navigation_header.view.*

class MainActivity : AppCompatActivity() {

    private lateinit var navController:NavController
    private lateinit var appBarConfiguration:AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        title = getString(R.string.customerList)
        val navView: NavigationView = findViewById(R.id.navigation_view)
        val navController = findNavController(R.id.nav_host_fragment)
        this.navController = navController
        setupToolBarMain()
        navView.setupWithNavController(this.navController)
        val headerView = navView.getHeaderView(0)
        headerView.textViewUserNameNavView.text = FragmentData.getUser()
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    private fun setupToolBarMain() {
        setSupportActionBar(toolBarMain)
        appBarConfiguration = AppBarConfiguration(setOf(R.id.home, R.id.shopFragment,R.id.customerListFragment,
                                                        R.id.saleList, R.id.userFragment),drawerLayoutMain)
        setupActionBarWithNavController(navController, appBarConfiguration)
    }
}


