package uz.bdmgroup.barakasavdo.screen.makeorder

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_order.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import uz.bdmgroup.barakasavdo.Utils.LocaleManager
import uz.bdmgroup.barakasavdo.MapsActivity
import uz.bdmgroup.barakasavdo.R
import uz.bdmgroup.barakasavdo.model.AdresModel
import uz.bdmgroup.barakasavdo.model.ProductModel

class OrderActivity : AppCompatActivity() {
    var comeAdress: AdresModel? = null
    lateinit var items: List<ProductModel>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order)

        items = intent.getSerializableExtra("go_product") as List<ProductModel>
        PriceOrder.setText(items.sumByDouble {
            it.cartCount.toDouble() * (it.price.replace(" ", "").toDoubleOrNull() ?: 0.0)
        }.toString())

        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this)
        }

        AddresOrder.setOnClickListener {
            startActivity(Intent(this, MapsActivity::class.java))
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this)
        }
    }

    @Subscribe
    fun onHelperEventBus(address: AdresModel) {
        this.comeAdress = address
        AddresOrder.setText("${address.latitude}, ${address.longitude}")
    }

    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(LocaleManager.setLocale(newBase))
    }
}