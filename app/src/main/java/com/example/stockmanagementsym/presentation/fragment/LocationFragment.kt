package com.example.stockmanagementsym.presentation.fragment

import android.annotation.SuppressLint
import android.location.Location
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

    @SuppressLint("MissingPermission")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fusedLocation = LocationServices.getFusedLocationProviderClient(requireContext())
        buttonToLocate.setOnClickListener {
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

    }
}