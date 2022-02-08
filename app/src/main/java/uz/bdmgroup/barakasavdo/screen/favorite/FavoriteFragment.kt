package uz.bdmgroup.barakasavdo.screen.favorite

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_favorite.*
import uz.bdmgroup.barakasavdo.MainViewModel
import uz.bdmgroup.barakasavdo.R
import uz.bdmgroup.barakasavdo.Utils.PrefUtils
import uz.bdmgroup.barakasavdo.view.ProductsAdapter

class FavoriteFragment : Fragment() {

    lateinit var viewModel: MainViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel=ViewModelProvider(this).get(MainViewModel::class.java)

        viewModel.topProductData.observe(this, Observer {
           recyclerfavoriteproduct.adapter=ProductsAdapter(it)
       })
        viewModel.error.observe(this, Observer {
            Toast.makeText(requireActivity(), it, Toast.LENGTH_LONG).show()
        })
        viewModel.progress.observe(this, Observer {
            Swipe.isRefreshing=it
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerfavoriteproduct.layoutManager=LinearLayoutManager(requireActivity())

        Swipe.setOnRefreshListener {
            loadData()
        }
        loadData()

    }
    fun loadData() {
        viewModel.getProducstByIdsrequest(PrefUtils.getFavoriteList())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorite, container, false)
    }

    companion object {

        @JvmStatic
        fun newInstance() = FavoriteFragment()
}
}