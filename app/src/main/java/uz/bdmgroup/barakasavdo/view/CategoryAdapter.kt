package uz.bdmgroup.barakasavdo.view

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.category_item_layout.view.*
import uz.bdmgroup.barakasavdo.R
import uz.bdmgroup.barakasavdo.model.CategoryModel

interface CategoryAdapterListener{
    fun onClickCategory(item:CategoryModel)
}

class CategoryAdapter (val items:List<CategoryModel>, val callback:CategoryAdapterListener):RecyclerView.Adapter<CategoryAdapter.ItemHolder>(){
    class ItemHolder(view:View):RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        return ItemHolder(LayoutInflater.from(parent.context).inflate(R.layout.category_item_layout, parent, false))
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        val item=items[position]
        holder.itemView.setOnClickListener(){
            items.forEach(){
                it.checked=false
            }
            item.checked=true
            callback.onClickCategory(item)
            notifyDataSetChanged()
        }
        holder.itemView.tvTittle.text=item.title

        if (item.checked==true){
            holder.itemView.cardview.setCardBackgroundColor(ContextCompat.getColor(holder.itemView.context, R.color.colorPrimary))
            holder.itemView.tvTittle.setTextColor(Color.WHITE)
        }
        else{
            holder.itemView.cardview.setCardBackgroundColor(ContextCompat.getColor(holder.itemView.context, R.color.gray))
            holder.itemView.tvTittle.setTextColor(Color.BLACK)
        }

    }

    override fun getItemCount(): Int {
        return items.count()
    }
}