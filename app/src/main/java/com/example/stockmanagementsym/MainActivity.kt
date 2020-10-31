package com.example.stockmanagementsym

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.location.Location
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
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
import com.example.stockmanagementsym.data.CONSTANTS
import com.example.stockmanagementsym.presentation.fragment.FragmentData
import com.google.android.gms.location.*
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.material.navigation.NavigationView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_new_product.*
import kotlinx.android.synthetic.main.fragment_new_user.*
import kotlinx.android.synthetic.main.layout_navigation_header.*
import kotlinx.android.synthetic.main.layout_navigation_header.view.*

class MainActivity : AppCompatActivity() {

    private lateinit var navController:NavController
    private lateinit var appBarConfiguration:AppBarConfiguration
    
    private var androidLocation: AndroidLocation ?= null
    private var fusedLocation: FusedLocationProviderClient ?= null
    private var locationObserver:Boolean = false

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

        val userPhotoData = FragmentData.getUserPhotoData()

        if(userPhotoData!=""){
            if(userPhotoData.length<400){
                try {
                    Picasso.get().load(userPhotoData).into(headerView.imageViewUserNavHeader)
                    headerView.imageViewUserNavHeader.background = null
                }catch (e:Exception){
                    FragmentData.showToastMessage(headerView.context, ""+e)
                }
            }else{
                headerView.imageViewUserNavHeader.setImageBitmap(FragmentData.getBitMapFromString(userPhotoData))
                headerView.imageViewUserNavHeader.background = null
            }
        }

        if((userLatitude==CONSTANTS.DEFAULT_USER_LATITUDE)&&(userLongitude==CONSTANTS.DEFAULT_USER_LONGITUDE))
            headerView.textViewUserLocationNavView.text =
                getString(R.string.locationFailure)
        else
            headerView.textViewUserLocationNavView.text =
                getString(R.string.location)+" "+userLatitude+" "+userLongitude

        headerView.buttonLocation.setOnClickListener {
            locationObserver = false
            checkPermission()
        }
        headerView.buttonLocationObs.setOnClickListener {
            locationObserver = true
            checkPermission()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == 10 && resultCode == RESULT_OK){
            val bitMap = data?.extras?.get("data") as Bitmap

            imageViewNewProduct?.setImageBitmap(bitMap)
            imageViewNewProduct?.visibility = View.VISIBLE
            imageViewNewUser?.setImageBitmap(bitMap)
            imageViewNewUser?.visibility = View.VISIBLE

            FragmentData.setBitMap(bitMap)
        }
        if(requestCode == 101 && resultCode == RESULT_OK){
            val imageUri = data!!.data!!

            imageViewNewProduct?.setImageURI(imageUri)
            imageViewNewProduct?.visibility = View.VISIBLE
            imageViewNewUser?.setImageURI(imageUri)
            imageViewNewUser?.visibility = View.VISIBLE


            val bitMap = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                ImageDecoder.decodeBitmap(ImageDecoder.createSource(this.contentResolver, imageUri))
            } else {
                MediaStore.Images.Media.getBitmap(this.contentResolver, imageUri)
            }
            FragmentData.setBitMap(bitMap)
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
        if(locationObserver){
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

private class AndroidLocation(private val textView: TextView, val context: Context) :
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
            textView.text = """Localización: ${location.lastLocation.latitude} - ${location.lastLocation.longitude}"""
            FragmentData.setUserLocation(location.lastLocation.latitude, location.lastLocation.longitude)
        }else{
            FragmentData.showToastMessage(context, textView.context.getString(R.string.locationFailure))
        }
    }
    override fun onFailure(p0: Exception) {
        FragmentData.showToastMessage(context, textView.context.getString(R.string.locationFailure))
    }
}

