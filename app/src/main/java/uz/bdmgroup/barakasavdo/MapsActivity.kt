package uz.bdmgroup.barakasavdo

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import org.greenrobot.eventbus.EventBus
import uz.bdmgroup.barakasavdo.Utils.LocaleManager
import uz.bdmgroup.barakasavdo.databinding.ActivityMapsBinding
import uz.bdmgroup.barakasavdo.model.AdresModel

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        binding.GoAdres.setOnClickListener {
            val adresModel=AdresModel("", mMap.cameraPosition.target.latitude, mMap.cameraPosition.target.longitude)
            EventBus.getDefault().post(adresModel)
            finish()
        }
    }


    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

    }

    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(LocaleManager.setLocale(newBase))
    }
}