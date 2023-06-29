package com.example.minorproject

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_park_map.*
import kotlinx.android.synthetic.main.activity_register.*


class ParkMapActivity : AppCompatActivity(), OnMapReadyCallback {
    private lateinit var currentLocation: Location
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private val permissionCode = 101
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_park_map)
        fusedLocationProviderClient =
            LocationServices.getFusedLocationProviderClient(this@ParkMapActivity)
        fetchLocation()

        var prefer=getSharedPreferences("MyPref", MODE_PRIVATE)
        var str=prefer.getString("UserName","wrong")

        btnParkHere.setOnClickListener {
            val intent = Intent(applicationContext, TimingActivity::class.java)
            startActivity(intent)
        }
    }

    private fun fetchLocation() {
        if (ActivityCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_FINE_LOCATION
            ) !=
            PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_COARSE_LOCATION
            ) !=
            PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), permissionCode
            )
            return
        }
        val task = fusedLocationProviderClient.lastLocation
        task.addOnSuccessListener {
            if (it != null) {
                currentLocation = it
                Toast.makeText(
                    applicationContext, currentLocation.latitude.toString() + "" +
                            currentLocation.longitude, Toast.LENGTH_SHORT
                ).show()
                val supportMapFragment = (supportFragmentManager.findFragmentById(R.id.map) as
                        SupportMapFragment?)!!
                supportMapFragment.getMapAsync(this@ParkMapActivity)
            }
        }

    }

    override fun onMapReady(googleMap: GoogleMap?) {
        val latLng = LatLng(currentLocation.latitude, currentLocation.longitude)
        Log.d("yyy", currentLocation.latitude.toString())
        //currentLocation.latitude, currentLocation.longitude
        //currentLocation.latitude, currentLocation.longitude
        val markerOptions = MarkerOptions().position(latLng).title("I am here!")
            .icon(BitmapFromVector(getApplicationContext(), R.drawable.ic_baseline_directions_car_24))
        googleMap?.animateCamera(CameraUpdateFactory.newLatLng(latLng))
        googleMap?.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 5f))
        googleMap?.addMarker(markerOptions)

        val name = "Parked Here"
        val lat = currentLocation.latitude
        val lng = currentLocation.longitude
        val savelocation = SaveLocation(lat, lng)
        val db = FirebaseDatabase.getInstance().getReference("Location")
        db.child(name).setValue(savelocation).addOnSuccessListener {
            Toast.makeText(applicationContext, "Location Saved", Toast.LENGTH_LONG).show()
        }
    }

    private fun BitmapFromVector(Context: Context, vectorResId: Int): BitmapDescriptor? {
        // below line is use to generate a drawable.
        val vectorDrawable = ContextCompat.getDrawable(Context, vectorResId)
        // below line is use to set bounds to our vector drawable.
        vectorDrawable!!.setBounds(
            0,
            0,
            vectorDrawable!!.intrinsicWidth,
            vectorDrawable!!.intrinsicHeight
        )
        // below line is use to create a bitmap for our drawable which we have added.
        val bitmap = Bitmap.createBitmap(
            vectorDrawable!!.intrinsicWidth,
            vectorDrawable!!.intrinsicHeight,
            Bitmap.Config.ARGB_8888
        )
        // below line is use to add bitmap in our canvas.
        val canvas = Canvas(bitmap)
        // below line is use to draw our vector drawable in canvas.
        vectorDrawable!!.draw(canvas)
        // after generating our bitmap we are returning our bitmap.
        return BitmapDescriptorFactory.fromBitmap(bitmap)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            permissionCode -> if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                fetchLocation()
            }
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.mymenu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        var id=item.itemId
        if(id==R.id.item1)
        {
            var prefer=getSharedPreferences("MyPref", MODE_PRIVATE)
            var editor=prefer.edit()
            editor.clear()
            editor.commit()
            var intent = Intent(applicationContext,LoginActivity::class.java)
            startActivity(intent)
            finish()
            Toast.makeText(applicationContext,"Logout successfully",Toast.LENGTH_LONG).
            show()
        }
        else if(id==R.id.item2)
        {
            val intent = Intent(this,FeedbackActivity::class.java)
            startActivity(intent)
        }
        return super.onOptionsItemSelected(item)
    }
}
