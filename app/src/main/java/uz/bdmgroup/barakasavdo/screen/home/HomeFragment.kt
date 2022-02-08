package uz.bdmgroup.barakasavdo.screen.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.fragment_home.*
import uz.bdmgroup.barakasavdo.MainViewModel
import uz.bdmgroup.barakasavdo.R
import uz.bdmgroup.barakasavdo.model.CategoryModel
import uz.bdmgroup.barakasavdo.model.OfferModel
import uz.bdmgroup.barakasavdo.view.CategoryAdapter
import uz.bdmgroup.barakasavdo.view.CategoryAdapterListener
import uz.bdmgroup.barakasavdo.view.ProductsAdapter


class HomeFragment : Fragment() {
    var offers:List<OfferModel> = emptyList()
   lateinit var viewModel: MainViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
   viewModel=ViewModelProvider(this).get(MainViewModel::class.java)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //refresh layout ishga tushirish
        swiperefresh.setOnRefreshListener {
            loadData()
        }


        viewModel.error.observe(requireActivity(), Observer {
            Toast.makeText(requireActivity(), it, Toast.LENGTH_LONG).show()
        })

        viewModel.offersdata.observe(requireActivity(), Observer {
            carouselView.setImageListener{ position, imageView->
                Glide.with(imageView).load("http://osonsavdo.devapp.uz/images/${it[position].image}").into(imageView)
            }
            carouselView.pageCount=it.count()
        })

        viewModel.categoriessData.observe(requireActivity(), Observer {
            recyclerCategories.layoutManager=LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)
            recyclerCategories.adapter=CategoryAdapter(it, object :CategoryAdapterListener{
                override fun onClickCategory(item: CategoryModel) {
                    viewModel.getProductByCategories(item.id)

                }
            })
        })

        viewModel.topProductData.observe(requireActivity(), Observer {
            recyclerProducts.layoutManager=LinearLayoutManager(requireActivity())
            recyclerProducts.adapter=ProductsAdapter(it)
        })

        viewModel.progress.observe(requireActivity(), Observer {
            swiperefresh.isRefreshing=it
        })
        loadData()
    }

    fun loadData(){
        viewModel.getOffers()
        viewModel.getAllDBCategories()
        viewModel.getAllDBproducts()
    }

    companion object {

        @JvmStatic
        fun newInstance() =HomeFragment()
    }
}

