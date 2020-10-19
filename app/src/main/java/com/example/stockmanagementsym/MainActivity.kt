package com.example.stockmanagementsym

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.stockmanagementsym.presentation.AndroidController
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var navController:NavController
    private lateinit var appBarConfiguration:AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
        navigationView.setupWithNavController(navController)
        navigationView.setNavigationItemSelectedListener(AndroidController)
        setupToolBarMain()
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    private fun setupToolBarMain() {
        setSupportActionBar(toolBarMain)
        appBarConfiguration = AppBarConfiguration(setOf(R.id.productsList,R.id.item_menu_customer_list,
                                                        R.id.item_menu_sale_list, R.id.item_menu_create_user),drawerLayoutMain)
        setupActionBarWithNavController(navController, appBarConfiguration)
    }
}


