package com.example.pruebatecnica.fragments

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.location.LocationRequest
import android.os.Bundle
import android.provider.Settings
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresPermission
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.location.LocationManagerCompat.isLocationEnabled
import com.example.pruebatecnica.MainActivity
import com.example.pruebatecnica.R
import com.example.pruebatecnica.adapters.PelisAdapter
import com.example.pruebatecnica.databinding.FragmentUbicacionBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import java.util.jar.Manifest


class ubicacionFragment : Fragment() {


    lateinit var mFusedLocationClient: FusedLocationProviderClient
    val PERMISSION_ID = 24

    private var _binding: FragmentUbicacionBinding? = null
    private val binding get() = _binding!!



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentUbicacionBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if(allPermisionGrandedGPS()){
            mFusedLocationClient = LocationServices.getFusedLocationProviderClient(activity as Activity)
            leerUbicacion()
        } else {
            ActivityCompat.requestPermissions(activity as Activity, arrayOf(android.Manifest.permission.ACCESS_COARSE_LOCATION,
            android.Manifest.permission.ACCESS_COARSE_LOCATION), PERMISSION_ID)
        }


       // checkPermisos()

    }

    private fun allPermisionGrandedGPS() = REQUIRED_PERMISSIONS_GPS.all {
        ContextCompat.checkSelfPermission(activity as Activity, it) == PackageManager.PERMISSION_GRANTED
    }

    companion object {
     private val REQUIRED_PERMISSIONS_GPS = arrayOf(android.Manifest.permission.ACCESS_COARSE_LOCATION, android.Manifest.permission.ACCESS_FINE_LOCATION)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode == PERMISSION_ID){
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Toast.makeText(activity, "Tenemos permiso para ubicacion", Toast.LENGTH_SHORT).show()
                    //guardar ubicacion
                mFusedLocationClient = LocationServices.getFusedLocationProviderClient(activity as Activity)
                leerUbicacion()
            }else {
                Toast.makeText(activity, "no hay permisos", Toast.LENGTH_SHORT).show()
            }
        }
    }



 private fun checkPermisos(){

     if(ContextCompat.checkSelfPermission(activity as Activity, android.Manifest.permission.ACCESS_FINE_LOCATION)==PackageManager.PERMISSION_GRANTED){
         Toast.makeText(activity, "Tenemos permiso para ubicacion", Toast.LENGTH_SHORT).show()
         //guardarubicacion
         mFusedLocationClient = LocationServices.getFusedLocationProviderClient(activity as Activity)
         leerUbicacion()
     }
     else {
        pedirPermisos()
     }

 }
    private fun pedirPermisos(){
        if(ActivityCompat.shouldShowRequestPermissionRationale(activity as Activity, android.Manifest.permission.ACCESS_FINE_LOCATION)){
            Toast.makeText(activity, "Tenemos permiso para ubicacion", Toast.LENGTH_SHORT).show()
        } else {
            ActivityCompat.requestPermissions(activity as Activity, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION,
            android.Manifest.permission.ACCESS_COARSE_LOCATION), PERMISSION_ID)
        }
    }


    @SuppressLint("MissingPermission")
    fun leerUbicacion(){


      if(checkPermissions()){
              if (ActivityCompat.checkSelfPermission(activity as Activity, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(activity as Activity, android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED)  {

                 mFusedLocationClient.lastLocation.addOnCompleteListener(activity as Activity){task ->
                     var location: Location? = task.result
                     if (location == null){

                     } else {
                         binding.txtUbi.text = "LATITUD = " + location.latitude.toString()
                         binding.txtUbi2.text = "LONGUITUD = " + location.longitude.toString()


                     }
                 }
              }

      } else {
            ActivityCompat.requestPermissions(activity as Activity, arrayOf(android.Manifest.permission.ACCESS_COARSE_LOCATION, android.Manifest.permission.ACCESS_FINE_LOCATION), PERMISSION_ID )
      }

    }


    private fun checkPermissions() : Boolean{
        if (ActivityCompat.checkSelfPermission(activity as Activity, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(activity as Activity, android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            return true
        }
        return false
    }
}