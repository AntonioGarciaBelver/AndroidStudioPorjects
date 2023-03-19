package activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.ComunidadCRUD
import com.example.ud10_cp1.R

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.example.ud10_cp1.databinding.ActivityMapsBinding
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import models.Comunidad

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding
    var banderas: MutableList<Comunidad> = mutableListOf()
    val comunidadCrud= ComunidadCRUD(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        mMap.mapType = GoogleMap.MAP_TYPE_SATELLITE
        var cameraPosition = CameraPosition(LatLng(36.719726, -4.421611), 1.0f, 45.0f, 0.0f)
        var cameraUpdate = CameraUpdateFactory.newCameraPosition(cameraPosition)

        cameraPosition=mMap.cameraPosition
        val posicion="Latitud:" + cameraPosition.target.latitude + " Longitud:" + cameraPosition.target.longitude

        var latitud = intent.getDoubleExtra("latitud", 0.0)
        var longitud = intent.getDoubleExtra("longitud", 0.0)

        val comunidadActual = LatLng(latitud, longitud)
        cameraPosition =
            CameraPosition.Builder().target(comunidadActual).zoom(6.0f).bearing(45.0f).tilt(70.0f).build()
        cameraUpdate = CameraUpdateFactory.newCameraPosition(cameraPosition)

        banderas = comunidadCrud.getComunidades()
        for (comunidad in banderas){
            mMap.addMarker(
                MarkerOptions().position(LatLng(comunidad.latitud, comunidad.longitud))
                    .icon(BitmapDescriptorFactory.fromResource(comunidad.icono))
                    .title(comunidad.nombre+" - Capital: "+comunidad.capital).snippet("Habitantes: "+comunidad.habitantes)
            )
        }

        mMap.animateCamera(cameraUpdate)

        mMap.setOnMarkerClickListener { marker ->
            val toast = Toast.makeText(
                this@MapsActivity,
                marker.title, Toast.LENGTH_SHORT
            )
            toast.show()
            false
        }

        mMap.setOnMapClickListener { latLng ->
            val toast = Toast.makeText(
                this@MapsActivity,
                "Latitud:" + latLng.latitude +
                        " Longitud:" + latLng.longitude,
                Toast.LENGTH_SHORT
            )
            toast.show()
        }
    }
}