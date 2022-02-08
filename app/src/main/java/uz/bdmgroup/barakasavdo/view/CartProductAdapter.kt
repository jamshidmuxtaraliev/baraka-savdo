package uz.bdmgroup.barakasavdo.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.cart_item_layout.view.*
import uz.bdmgroup.barakasavdo.R
import uz.bdmgroup.barakasavdo.Utils.Constants
import uz.bdmgroup.barakasavdo.Utils.PrefUtils
import uz.bdmgroup.barakasavdo.model.ProductModel

class CartProductAdapter(private val items:List<ProductModel>):RecyclerView.Adapter<CartProductAdapter.ItemHolder>() {
    class ItemHolder(view: View):RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        return ItemHolder(LayoutInflater.from(parent.context).inflate(R.layout.cart_item_layout, parent, false))
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
       val item=items[position]
        holder.itemView.NomiCart.text=item.name
        holder.itemView.NarxiCart.text=item.price
        holder.itemView.countProduct_cart.text=PrefUtils.getCartCount(item).toString()
        Glide.with(holder.itemView).load(Constants.HOSTING_IMAGE+item.image).into(holder.itemView.RasmiCart)
    }

    override fun getItemCount(): Int {
        return items.count()
    }
}