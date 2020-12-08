package hu.szurdok.todoapp.ui.view.main.task.create

import android.app.Activity
import android.content.Intent
import android.location.Geocoder
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.Autocomplete
import com.google.android.libraries.places.widget.AutocompleteActivity
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode
import hu.szurdok.todoapp.R
import kotlinx.android.synthetic.main.activity_create_map.*
import java.util.*


class CreateMapActivity : AppCompatActivity(), OnMapReadyCallback {
    private var mMap: GoogleMap? = null
    private var geocoder : Geocoder? = null
    private var markerOptions : MarkerOptions? = MarkerOptions().position(LatLng(0.0, 0.0)).title("Middle of nowhere")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_map)

        Places.initialize(applicationContext, "AIzaSyAdOXtnlBDUdQWlkoilSkL_K-0VeQlsDrs")

        geocoder = Geocoder(this, Locale.getDefault())

        btMapSearch.setOnClickListener {
            val fields = listOf(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG)
            val intent = Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY, fields)
                .build(this)
            startActivityForResult(intent, 1)
        }

        btMapBack.setOnClickListener {
            finish()
        }

        btSelectPlace.setOnClickListener {
            val resultIntent = Intent().putExtra("result", markerOptions)
            setResult(RESULT_OK, resultIntent)
            finish()
        }
    }

    override fun onStart() {
        super.onStart()
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(p0: GoogleMap) {
        p0.addMarker(markerOptions)
        p0.uiSettings.isZoomControlsEnabled = true

        p0.setOnMapClickListener {
            p0.clear()

            var title = "No address"
            val places = geocoder!!.getFromLocation(it.latitude, it.longitude, 1)
            if(places.isNotEmpty()){
                val place = places[0]
                if(place.maxAddressLineIndex != -1){
                    title = place.getAddressLine(0)
                }
            }
            markerOptions = MarkerOptions().position(it).title(title)
            p0.addMarker(markerOptions)
        }

        mMap = p0
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == 1) {
            when (resultCode) {
                Activity.RESULT_OK -> {
                    data?.let {
                        val place = Autocomplete.getPlaceFromIntent(data)
                        mMap!!.clear()
                        markerOptions = MarkerOptions().title(place.name).position(place.latLng!!)
                        mMap!!.addMarker(markerOptions)
                    }
                }
                AutocompleteActivity.RESULT_ERROR -> {
                    data?.let {
                        val status = Autocomplete.getStatusFromIntent(data)
                        Log.i("maps", status.statusMessage?:"")
                    }
                }
                Activity.RESULT_CANCELED -> {
                    // The user canceled the operation.
                }
            }
            return
        }
        super.onActivityResult(requestCode, resultCode, data)
    }
}