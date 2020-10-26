package com.example.stockmanagementsym.presentation.fragment

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.example.stockmanagementsym.R
import com.example.stockmanagementsym.presentation.view.FragmentData
import com.google.android.gms.location.*
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import kotlinx.android.synthetic.main.fragment_location.*

class LocationFragment : Fragment(), OnSuccessListener<Location>, OnFailureListener {

    private lateinit var fusedLocation:FusedLocationProviderClient

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_location, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fusedLocation = LocationServices.getFusedLocationProviderClient(requireContext())
        buttonToLocate.setOnClickListener {
            if (ActivityCompat.checkSelfPermission(
                    this.requireContext(),
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                    this.requireContext(),
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                Log.d("Test location","else")
            }
            fusedLocation.lastLocation.addOnSuccessListener(this).addOnFailureListener(this)
        }

        buttonObserve.setOnClickListener {
            val locationRequest = LocationRequest()
            locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
            locationRequest.interval = 5
            val locationCallBack = object: LocationCallback(){
                override fun onLocationResult(location: LocationResult?) {
                    super.onLocationResult(location)
                    if(location != null){
                        textViewLatitudeObs.text = location.lastLocation.latitude.toString()
                        textViewLongitudeObs.text = location.lastLocation.longitude.toString()
                    }else{
                        FragmentData.showMessage(requireContext(), "No es posible encontrar la localización observer")
                    }
                }
            }
            //fusedLocation.requestLocationUpdates()
            
        }
    }

    override fun onSuccess(location: Location?) {
        if(location!=null){
            textViewLatitude.text = location.latitude.toString()
            textViewLongitude.text = location.longitude.toString()
        }else{
            FragmentData.showMessage(this.requireContext(), "No es posible encontrar la localización")
        }
    }

    override fun onFailure(p0: Exception) {
        FragmentData.showMessage(this.requireContext(), "No es posible encontrar la localización")
    }
}