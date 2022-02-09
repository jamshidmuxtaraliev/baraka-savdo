package uz.bdmgroup.barakasavdo.screen.makeorder

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_order.*
import kotlinx.android.synthetic.main.fragment_cart.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import uz.bdmgroup.barakasavdo.MainViewModel
import uz.bdmgroup.barakasavdo.MapsActivity
import uz.bdmgroup.barakasavdo.R
import uz.bdmgroup.barakasavdo.Utils.LocaleManager
import uz.bdmgroup.barakasavdo.model.AdresModel
import uz.bdmgroup.barakasavdo.model.CartModel
import uz.bdmgroup.barakasavdo.model.ProductModel

class OrderActivity : AppCompatActivity() {
    var comeAdress: AdresModel? = null
    lateinit var viewModel: MainViewModel
    lateinit var items: List<ProductModel>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)


        viewModel.error.observe(this, Observer {
            Toast.makeText(this, it, Toast.LENGTH_LONG).show()
        })
        viewModel.progress.observe(this, Observer {
            flProgress.visibility = if (it) View.VISIBLE else View.GONE
        })


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

        ButtonOrder.setOnClickListener {
            viewModel.makeOrder(items.map { CartModel(it.id, it.cartCount) }, comeAdress?.latitude ?: 0.0, comeAdress?.longitude ?: 0.0, CommentOrder.text.toString())
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