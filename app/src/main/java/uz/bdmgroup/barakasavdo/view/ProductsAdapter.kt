package uz.bdmgroup.barakasavdo.view

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.product_item_layout.view.*
import uz.bdmgroup.barakasavdo.R
import uz.bdmgroup.barakasavdo.Utils.Constants
import uz.bdmgroup.barakasavdo.model.ProductModel
import uz.bdmgroup.barakasavdo.productDetil.ProductDetailActivity

class ProductsAdapter(val items:List<ProductModel>):RecyclerView.Adapter<ProductsAdapter.ItemHolder>(){
    inner class ItemHolder(view: View): RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        return ItemHolder(LayoutInflater.from(parent.context).inflate(R.layout.product_item_layout, parent, false))
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        val item=items[position]

        holder.itemView.setOnClickListener(){
            val intent=Intent(it.context, ProductDetailActivity::class.java)
            intent.putExtra("go_product", item)
            it.context.startActivity(intent)
        }

        holder.itemView.Nomi.text=item.name
        holder.itemView.Narxi.text=item.price
        Glide.with(holder.itemView.context).load(Constants.HOSTING_IMAGE+item.image).into(holder.itemView.Rasmi)

    }

    override fun getItemCount(): Int {
        return items.count()
    }
}
