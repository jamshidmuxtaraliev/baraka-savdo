package uz.bdmgroup.barakasavdo.screen.cart

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_cart.*
import uz.bdmgroup.barakasavdo.MainViewModel
import uz.bdmgroup.barakasavdo.R
import uz.bdmgroup.barakasavdo.Utils.PrefUtils
import uz.bdmgroup.barakasavdo.model.ProductModel
import uz.bdmgroup.barakasavdo.screen.makeorder.OrderActivity
import uz.bdmgroup.barakasavdo.view.CartProductAdapter
import java.io.Serializable

class CartFragment : Fragment() {
    lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel=ViewModelProvider(this).get(MainViewModel::class.java)

        viewModel.progress.observe(this, Observer {
            swipeCart.isRefreshing=it
        })
        viewModel.error.observe(this, Observer {
            Toast.makeText(requireActivity(), it, Toast.LENGTH_LONG).show()
        })
        viewModel.topProductData.observe(this, Observer {
            recylerCart.adapter=CartProductAdapter(it)
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_cart, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recylerCart.layoutManager=LinearLayoutManager(requireActivity())

        swipeCart.setOnRefreshListener {
            loadData()
        }

        btnOrder.setOnClickListener {
            val intent=Intent(requireActivity(), OrderActivity::class.java)
            intent.putExtra("go_product", (viewModel.topProductData.value?: emptyList<ProductModel>())as Serializable)
            startActivity(intent)
        }

        loadData()
    }

    fun loadData(){
        viewModel.getProducstByIdsrequest(PrefUtils.getCartList().map { it.product_id })
    }

    companion object {
        @JvmStatic
        fun newInstance() = CartFragment()
    }
}