package com.example.stockmanagementsym

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.stockmanagementsym.data.CONSTANTS
import com.example.stockmanagementsym.logic.AndroidActivityResult
import com.example.stockmanagementsym.logic.NavigationViewLogic
import com.example.stockmanagementsym.presentation.AndroidModel
import com.example.stockmanagementsym.presentation.fragment.FragmentData
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), ILauncher {

    private var navigationViewLogic: NavigationViewLogic? = null
    private var androidActivityResult: AndroidActivityResult? = null
    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        FragmentData.setContext(this)
        FragmentData.setAndroidActivityResult(getAndroidActivityResult())

        navController = findNavController(R.id.nav_host_fragment)

        setupToolBarMain()

        val navView: NavigationView = findViewById(R.id.navigation_view)
        navView.setupWithNavController(navController)

        val headerView = navView.getHeaderView(0)
        getNavigationViewLogic().loadHeaderViewComponents(headerView)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        getAndroidActivityResult().onActivityResult(requestCode, resultCode, data)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun onRequestPermissionsResult(
            requestCode: Int,
            permissions: Array<out String>,
            grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == CONSTANTS.LOCATION_INTENT_CODE)
            if (!grantResults.all { it == PackageManager.PERMISSION_GRANTED })
                FragmentData.showAlertMessage(R.string.location,R.string.locationFailure)
            else
                getNavigationViewLogic().loadLocation() //Permission was granted by user
    }

    private fun setupToolBarMain() {
        setSupportActionBar(toolBarMain)
        appBarConfiguration = AppBarConfiguration(setOf(R.id.home, R.id.shopFragment,R.id.customerListFragment,
                                                        R.id.saleList, R.id.userFragment, R.id.locationFragment, R.id.buttonLogOut),drawerLayoutMain)
        setupActionBarWithNavController(navController, appBarConfiguration)
    }

    fun askSaveLocation(item: MenuItem) {
        FragmentData.askSaveLocation()
    }

    private fun getAndroidActivityResult(): AndroidActivityResult {
        if (androidActivityResult == null)
            androidActivityResult = AndroidActivityResult(this, this,this)
        return androidActivityResult!!
    }

    override fun getAndroidModel(): AndroidModel {
        return FragmentData.getAndroidModel()
    }

    private fun getNavigationViewLogic():NavigationViewLogic{
        if (navigationViewLogic == null)
            navigationViewLogic = NavigationViewLogic(this)
        return navigationViewLogic!!
    }
}

