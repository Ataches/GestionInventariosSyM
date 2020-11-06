package com.example.stockmanagementsym.logic

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.pm.PackageManager
import android.location.Location
import android.view.View
import android.widget.TextView
import androidx.core.app.ActivityCompat
import com.example.stockmanagementsym.R
import com.example.stockmanagementsym.data.CONSTANTS
import com.example.stockmanagementsym.presentation.fragment.FragmentData
import com.google.android.gms.location.*
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import kotlinx.android.synthetic.main.layout_navigation_header.view.*

/*
    Created by Juan Sebastian Sanchez Mancilla on 4/11/2020
*/
class NavigationViewLogic(private val activity: Activity) {

    private lateinit var textViewUserLocation: TextView
    private var androidLocation: AndroidLocation? = null
    private var fusedLocation: FusedLocationProviderClient? = null
    private var locationObserver: Boolean = false

    fun loadHeaderViewComponents(headerView: View) {
        headerView.textViewUserNameNavView.text = FragmentData.getUserName()
        headerView.textViewUserPrivilegeNavView.text = FragmentData.getUserPrivilege()
        val userLatitude = FragmentData.getUserLatitude()
        val userLongitude = FragmentData.getUserLongitude()

        FragmentData.paintPhoto(
                FragmentData.getUserPhotoData(),
                headerView.imageViewUserNavHeader,
                R.drawable.ic_person
        )

        if ((userLatitude == CONSTANTS.DEFAULT_USER_LATITUDE) && (userLongitude == CONSTANTS.DEFAULT_USER_LONGITUDE))
            headerView.textViewUserLocationNavView.text = FragmentData.getString(R.string.locationFailure)
        else {
            headerView.textViewUserLocationNavView.text = FragmentData.getString(R.string.location) + " " + userLatitude + " " + userLongitude
        }

        textViewUserLocation = headerView.textViewUserLocationNavView

        headerView.buttonLocation.setOnClickListener {
            locationObserver = false
            checkPermission()
        }
        headerView.buttonLocationObs.setOnClickListener {
            locationObserver = true
            checkPermission()
        }
    }

    private fun checkPermission() {
        if (ActivityCompat.checkSelfPermission(FragmentData.getContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity,
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), CONSTANTS.LOCATION_INTENT_CODE)
        } else
            loadLocation() //The permission is al ready
    }

    @SuppressLint("MissingPermission")
    fun loadLocation() {
        if (androidLocation == null)
            androidLocation = AndroidLocation(textViewUserLocation)
        if (fusedLocation == null)
            fusedLocation = LocationServices.getFusedLocationProviderClient(activity)
        if (locationObserver) {
            val locationRequest = LocationRequest()
            locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
            locationRequest.interval = CONSTANTS.LOCATION_REQUEST_TIME_INTERVAL

            fusedLocation!!.requestLocationUpdates(locationRequest, androidLocation, null)
        } else {
            fusedLocation!!.lastLocation.addOnSuccessListener(androidLocation!!).addOnFailureListener(androidLocation!!)
        }
    }

}

private class AndroidLocation(private val textView: TextView) :
        OnSuccessListener<Location>, OnFailureListener, LocationCallback() {
    override fun onSuccess(location: Location?) = if (location != null) {
        textView.text = """Localización: ${location.latitude} - ${location.longitude}"""
        FragmentData.setUserLocation(location.latitude, location.longitude)
    } else {
        FragmentData.showToastMessage(R.string.locationFailure)
    }

    override fun onLocationResult(location: LocationResult?) {
        super.onLocationResult(location)
        if (location != null) {
            textView.text = """Localización: ${location.lastLocation.latitude} - ${location.lastLocation.longitude}"""
            FragmentData.setUserLocation(location.lastLocation.latitude, location.lastLocation.longitude)
        } else {
            FragmentData.showToastMessage(R.string.locationFailure)
        }
    }

    override fun onFailure(p0: Exception) {
        FragmentData.showToastMessage(R.string.locationFailure)
    }
}
