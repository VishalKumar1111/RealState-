package com.rlogixx.realstate.NewProperty

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Base64
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.rlogixx.realstate.R
import java.io.ByteArrayOutputStream
import com.google.gson.JsonObject
import com.rlogixx.realstate.API.ApiInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NewProperty : AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnMapClickListener {
    private var mGoogleMap: GoogleMap? = null
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var imageView: ImageView

    private var basicNeed: String = ""

    private var latitude = ""
    private var longitude = ""
    private val arrBasicNeeds = mutableListOf<String>()
    private lateinit var dict: MutableMap<String, String>
    private var imageString: String? = null

    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1001
        private const val CAMERA_PERMISSION_REQUEST_CODE = 101
        private const val GALLERY_PERMISSION_REQUEST_CODE = 102
        private const val CAMERA_REQUEST_CODE = 103
        private const val GALLERY_REQUEST_CODE = 104
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_property)

        val power = findViewById<ImageView>(R.id.power)
        val water = findViewById<ImageView>(R.id.water)
        val hospital = findViewById<ImageView>(R.id.hospital)
        val metro = findViewById<ImageView>(R.id.metro)
        val gym = findViewById<ImageView>(R.id.gym)
        val airport = findViewById<ImageView>(R.id.airport)
        val school = findViewById<ImageView>(R.id.school)
        val park = findViewById<ImageView>(R.id.park)
        val parking = findViewById<ImageView>(R.id.parking)
        val restaurant = findViewById<ImageView>(R.id.restaurant)
        val submit = findViewById<Button>(R.id.btnsubmit)

        dict = mutableMapOf()

        submit.setOnClickListener {
            val phoneNumber = findViewById<EditText>(R.id.txtfldcontact).text.toString()
            val name = findViewById<EditText>(R.id.txtfldname).text.toString()
            val address = findViewById<EditText>(R.id.txtfldaddress).text.toString()

            // Validate phone number
            if (!isValidPhoneNumber(phoneNumber)) {
                Toast.makeText(this, "Invalid phone number", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Validate name
            if (name.isEmpty()) {
                Toast.makeText(this, "Please enter your name", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Validate address
            if (address.isEmpty()) {
                Toast.makeText(this, "Please enter your address", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // All fields are valid, proceed with form submission
            for (x in arrBasicNeeds) {
                basicNeed = "$basicNeed$x,"
            }

            // Update the dictionary with the data
            dict["propertyname"] = name
            dict["contact"] = phoneNumber
            dict["propertylocation"] = address
            dict["landmark"] = findViewById<EditText>(R.id.landmarknearby).text.toString()
            dict["longitude"] = longitude
            dict["latitude"] = latitude
            dict["pincode"] = "226021"
            dict["basicneed"] = basicNeed.dropLast(1)
            dict["imagestring"] = imageString ?: ""

            // Call function to post property data
            callForPropertyData()
        }

        power.setOnClickListener { toggleBasicNeed(power, "Power") }
        water.setOnClickListener { toggleBasicNeed(water, "Water") }
        hospital.setOnClickListener { toggleBasicNeed(hospital, "Hospital") }
        metro.setOnClickListener { toggleBasicNeed(metro, "Metro") }
        gym.setOnClickListener { toggleBasicNeed(gym, "Gym") }
        airport.setOnClickListener { toggleBasicNeed(airport, "Airport") }
        school.setOnClickListener { toggleBasicNeed(school, "School") }
        park.setOnClickListener { toggleBasicNeed(park, "Park") }
        parking.setOnClickListener { toggleBasicNeed(parking, "Parking") }
        restaurant.setOnClickListener { toggleBasicNeed(restaurant, "Restaurant") }

        imageView = findViewById(R.id.img_choose)

        imageView.setOnClickListener {
            showImageOptionsDialog()
        }

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    private fun toggleBasicNeed(imageView: ImageView, basicNeed: String) {
        val isAdding = imageView.tag == null // Check if the tag is null, if yes, need to add the basic need
        if (isAdding) {
            imageView.setBackgroundResource(R.drawable.border_blue_shape) // Set blue border with radius
            imageView.tag = basicNeed // Set the tag to the basic need name
            arrBasicNeeds.add(basicNeed)
        } else {
            imageView.setBackgroundColor(Color.TRANSPARENT) // Remove background color
            imageView.tag = null // Remove the tag
            arrBasicNeeds.remove(basicNeed)
        }
    }

    private fun showImageOptionsDialog() {
        val options = arrayOf<CharSequence>("Camera", "Gallery", "Cancel")

        val builder = AlertDialog.Builder(this)
        builder.setTitle("Choose Image")
        builder.setItems(options) { dialog, item ->
            when {
                options[item] == "Camera" -> checkCameraPermission()
                options[item] == "Gallery" -> checkGalleryPermission()
                options[item] == "Cancel" -> dialog.dismiss()
            }
        }
        builder.show()
    }

    private fun checkCameraPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
            != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.CAMERA),
                CAMERA_PERMISSION_REQUEST_CODE
            )
        } else {
            openCamera()
        }
    }

    private fun checkGalleryPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
            != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                GALLERY_PERMISSION_REQUEST_CODE
            )
        } else {
            openGallery()
        }
    }

    private fun openCamera() {
        val takePicture = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(takePicture, CAMERA_REQUEST_CODE)
    }

    private fun openGallery() {
        val pickPhoto = Intent(
            Intent.ACTION_PICK,
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        )
        startActivityForResult(pickPhoto, GALLERY_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                CAMERA_REQUEST_CODE -> {
                    val imageBitmap = data?.extras?.get("data") as Bitmap
                    imageView.setImageBitmap(imageBitmap)
                    encodeImage(imageBitmap)
                }
                GALLERY_REQUEST_CODE -> {
                    val selectedImage: Uri? = data?.data
                    imageView.setImageURI(selectedImage)
                    val imageBitmap =
                        MediaStore.Images.Media.getBitmap(this.contentResolver, selectedImage)
                    encodeImage(imageBitmap)
                }
            }
        }
    }

    private fun encodeImage(imageBitmap: Bitmap) {
        val byteArrayOutputStream = ByteArrayOutputStream()
        imageBitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream)
        val byteArray: ByteArray = byteArrayOutputStream.toByteArray()
        imageString = Base64.encodeToString(byteArray, Base64.DEFAULT)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mGoogleMap = googleMap
        mGoogleMap?.setOnMapClickListener(this)

        // Check and request location permission
        checkLocationPermission()
    }

    override fun onMapClick(latlng: LatLng) {
        // Add a marker on the clicked location
        mGoogleMap?.addMarker(MarkerOptions().position(latlng))
    }

    private fun callForPropertyData() {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://rashitalk.com/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val propertyService = retrofit.create(ApiInterface::class.java)

        val propertyData = PropertyData(
            propertyname = findViewById<EditText>(R.id.txtfldname).toString(),
            contact = findViewById<EditText>(R.id.txtfldcontact).text.toString(),
            propertylocation = findViewById<EditText>(R.id.txtfldaddress).text.toString(),
            landmark = findViewById<EditText>(R.id.landmarknearby).text.toString(),
            longitude = longitude.toString(),
            latitude = latitude.toString(),
            pincode = 226021,
            basicneed = basicNeed.dropLast(1), // Remove the last comma
            imagestring = imageString ?: ""
        )

        val call = propertyService.addProperty(propertyData)
        call.enqueue(object : Callback<JsonObject> {
            override fun onResponse(
                call: Call<JsonObject>,
                response: retrofit2.Response<JsonObject>
            ) {
                if (response.isSuccessful) {
                    Toast.makeText(this@NewProperty, "Successfully Send Data", Toast.LENGTH_SHORT).show()
                } else {
                    Log.d("error post", "not working")
                    // Handle unsuccessful response
                }
            }

            override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                // Handle failure
            }
        })
    }

    private fun checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                LOCATION_PERMISSION_REQUEST_CODE
            )
        } else {
            // Location permission already granted
            enableMyLocation()
        }
    }

    private fun enableMyLocation() {
        // Enable the location layer and zoom to current location
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        mGoogleMap?.isMyLocationEnabled = true
        fusedLocationClient.lastLocation.addOnSuccessListener { location ->
            location?.let {
                val currentLatLng = LatLng(it.latitude, it.longitude)
                longitude = it.longitude.toString()
                latitude = it.latitude.toString()

                mGoogleMap?.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 15f))
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, enable location layer
                enableMyLocation()
            } else {
                // Permission denied, show a message to the user
                Toast.makeText(
                    this,
                    "Location permission denied. Some features may not be available.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        // Handle other permission requests here...
    }

    private fun isValidPhoneNumber(phoneNumber: String): Boolean {
        // Implement your phone number validation logic here
        return phoneNumber.length == 10 && phoneNumber.all { it.isDigit() }
    }
}
