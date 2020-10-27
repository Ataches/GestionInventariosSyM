package com.example.stockmanagementsym.presentation.fragment

import android.os.Bundle
import com.example.stockmanagementsym.R
import com.example.stockmanagementsym.presentation.view.FragmentData
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class LocationFragment : SupportMapFragment(), OnMapReadyCallback, GoogleMap.OnMapClickListener {

    private lateinit var map:GoogleMap
    private var userLatitude = -1.0
    private var userLongitude = -1.0

    override fun onActivityCreated(p0: Bundle?) {
        super.onActivityCreated(p0)
        getMapAsync(this)
    }

    override fun onMapReady(p0: GoogleMap) {
        map = p0
        loadUserLocation()

    }

    override fun onMapClick(p0: LatLng?) {
        loadUserLocation()
    }

    private fun loadUserLocation(){
        userLatitude = FragmentData.getUserLatitude()
        userLongitude = FragmentData.getUserLongitude()
        if((userLatitude != -1.0) && (userLongitude != -1.0)){
            val location = LatLng(userLatitude,userLongitude)
            map.addMarker(MarkerOptions().position(location).title(getString(R.string.location)))
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(location,120f))
            map.setOnMapClickListener(this)
            FragmentData.showToastMessage(this.requireContext(), getString(R.string.pressToReloadMap))
        }else
            FragmentData.showToastMessage(this.requireContext(),getString(R.string.locationFailure)+" "+getString(R.string.pressToReloadMap))
    }
}