package com.example.coffeecareerhub

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.example.coffeecareerhub.databinding.ActivityNearbyCoffeeShopsScreenBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.BitmapDescriptorFactory

class NearbyCoffeeShopsScreen : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityNearbyCoffeeShopsScreenBinding
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private val LOCATION_PERMISSION_REQUEST_CODE = 123


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityNearbyCoffeeShopsScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Check and request location permissions if not granted
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            // Permissions already granted, enable location
            enableLocation()
        } else {
            // Request location permissions
            ActivityCompat.requestPermissions(
                this,
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                LOCATION_PERMISSION_REQUEST_CODE
            )
        }
    }
    private fun enableLocation() {
        // Enable location layer on map
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }
        mMap.isMyLocationEnabled = true

        fusedLocationClient.lastLocation.addOnSuccessListener { location ->
            if (location != null) {
                val currentLatLng = LatLng(location.latitude, location.longitude)
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 15f))

                mMap.addMarker(MarkerOptions().position(currentLatLng).title("My Location").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)))
               val starbucks= mMap.addMarker(MarkerOptions().position(LatLng(38.45886489786062, 27.09460258474477)).title("Starbucks"))
                val kelvin= mMap.addMarker(MarkerOptions().position(LatLng(38.46187248886232, 27.089560031845377)).title("Kelvin Photo'n"))
                val goches= mMap.addMarker(MarkerOptions().position(LatLng(38.45703690326222, 27.09607747936497)).title("Goche's Coffee"))
                val lashabitas= mMap.addMarker(MarkerOptions().position(LatLng(38.458079726540326, 27.094951356084398)).title("Las Habitas"))
                val espello= mMap.addMarker(MarkerOptions().position(LatLng(38.459417377939424, 27.09863922269059)).title("Espello Coffee House"))
                val optimist= mMap.addMarker(MarkerOptions().position(LatLng(38.45661191655676, 27.097056516703297)).title("The Optimist Co."))

                mMap.setOnMarkerClickListener {clickedMarker->
                    if (clickedMarker==starbucks){
                        val intent= Intent(applicationContext,CoffeeShopDetailScreen::class.java)
                        intent.putExtra("name","starbucks")
                        startActivity(intent)
                    }
                    else if (clickedMarker==kelvin){
                        val intent=Intent(applicationContext,CoffeeShopDetailScreen::class.java)
                        intent.putExtra("name","kelvin")
                        startActivity(intent)
                    }
                    else if (clickedMarker==goches){
                        val intent=Intent(applicationContext,CoffeeShopDetailScreen::class.java)
                        intent.putExtra("name","goches")
                        startActivity(intent)
                    }
                    else if (clickedMarker==lashabitas){
                        val intent=Intent(applicationContext,CoffeeShopDetailScreen::class.java)
                        intent.putExtra("name","lashabitas")
                        startActivity(intent)
                    }
                    else if (clickedMarker==espello){
                        val intent=Intent(applicationContext,CoffeeShopDetailScreen::class.java)
                        intent.putExtra("name","espello")
                        startActivity(intent)
                    }
                    else if (clickedMarker==optimist){
                        val intent=Intent(applicationContext,CoffeeShopDetailScreen::class.java)
                        intent.putExtra("name","optimist")
                        startActivity(intent)
                    }
                    true
                }

            } else {
                Toast.makeText(this, "Location not available", Toast.LENGTH_SHORT).show()
            }
        }
    }
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Location permission granted, enable location
                enableLocation()
            } else {
                Toast.makeText(this, "Location permission denied", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
