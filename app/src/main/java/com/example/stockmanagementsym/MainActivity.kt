package com.example.stockmanagementsym

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.stockmanagementsym.presentation.view.FragmentData
import com.google.android.gms.location.*
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.material.navigation.NavigationView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.layout_navigation_header.*
import kotlinx.android.synthetic.main.layout_navigation_header.view.*

class MainActivity : AppCompatActivity() {

    private lateinit var navController:NavController
    private lateinit var appBarConfiguration:AppBarConfiguration
    
    private var androidLocation: AndroidLocation ?= null
    private var fusedLocation: FusedLocationProviderClient ?= null
    private var obs:Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navController = findNavController(R.id.nav_host_fragment)

        setupToolBarMain()

        val navView: NavigationView = findViewById(R.id.navigation_view)
        navView.setupWithNavController(navController)

        val headerView = navView.getHeaderView(0)
        headerView.textViewUserNameNavView.text = FragmentData.getUserName()
        headerView.textViewUserPrivilegeNavView.text = FragmentData.getUserPrivilege()
        val userLatitude = FragmentData.getUserLatitude()
        val userLongitude = FragmentData.getUserLongitude()

        headerView.imageViewCustomerNavView.visibility = View.VISIBLE
        val userPhotoData = FragmentData.getUserPhotoData()

        try{
            Picasso.get().load(userPhotoData).into(headerView.imageViewCustomerNavView)
            headerView.imageViewCustomerNavView.background = null
        }catch (e:Exception){
            Log.d("TEST IMAGE USER ","FAILED "+e)
            if(userPhotoData!=""){
                headerView.imageViewCustomerNavView.setImageBitmap(FragmentData.getBitMap())
                headerView.imageViewCustomerNavView.background = null
            }
        }

        if((userLatitude==-1.0)&&(userLongitude==-1.0))
            headerView.textViewUserLocationNavView.text =
                getString(R.string.locationFailure)
        else
            headerView.textViewUserLocationNavView.text =
                getString(R.string.location)+" "+userLatitude+" "+userLongitude

        headerView.buttonLocation.setOnClickListener {
            obs = false
            checkPermission()
        }
        headerView.buttonLocationObs.setOnClickListener {
            obs = true
            checkPermission()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    private fun setupToolBarMain() {
        setSupportActionBar(toolBarMain)
        appBarConfiguration = AppBarConfiguration(setOf(R.id.home, R.id.shopFragment,R.id.customerListFragment,
                                                        R.id.saleList, R.id.userFragment, R.id.locationFragment, R.id.buttonLogOut),drawerLayoutMain)
        setupActionBarWithNavController(navController, appBarConfiguration)
    }

    private fun checkPermission(){
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 102)
        }else
            loadLocation() //The permission is al ready
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode == 102)
            if(!grantResults.all{ it == PackageManager.PERMISSION_GRANTED})
                finish()
            else
                loadLocation() //Permission was granted by user
    }

    @SuppressLint("MissingPermission")
    private fun loadLocation() {
        if(androidLocation==null)
            androidLocation = AndroidLocation(textViewUserLocationNavView, this)
        if(fusedLocation==null)
            fusedLocation = LocationServices.getFusedLocationProviderClient(this)
        if(obs){
            val locationRequest = LocationRequest()
            locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
            locationRequest.interval = 10_000

            fusedLocation!!.requestLocationUpdates(locationRequest,androidLocation,null)
        }else{
            fusedLocation!!.lastLocation.addOnSuccessListener(androidLocation!!)?.addOnFailureListener(androidLocation!!)
        }
    }

    fun askSaveLocation(item: MenuItem) {
        FragmentData.askSaveLocation(this)
    }

}

class AndroidLocation(private val textView: TextView, val context: Context) :
    OnSuccessListener<Location>, OnFailureListener,LocationCallback(){
    override fun onSuccess(location: Location?) = if(location!=null){
        textView.text = """Localización: ${location.latitude} - ${location.longitude}"""
        FragmentData.setUserLocation(location.latitude, location.longitude)
    }else{
        FragmentData.showToastMessage(context, textView.context.getString(R.string.locationFailure))
    }

    override fun onLocationResult(location: LocationResult?) {
        super.onLocationResult(location)
        if(location != null){
            textView.text = """Localización: ${location!!.lastLocation.latitude} - ${location.lastLocation.longitude}"""
            FragmentData.setUserLocation(location.lastLocation.latitude, location.lastLocation.longitude)
        }else{
            FragmentData.showToastMessage(context, textView.context.getString(R.string.locationFailure))
        }
    }

    override fun onFailure(p0: Exception) {
        FragmentData.showToastMessage(context, textView.context.getString(R.string.locationFailure))
    }

}

