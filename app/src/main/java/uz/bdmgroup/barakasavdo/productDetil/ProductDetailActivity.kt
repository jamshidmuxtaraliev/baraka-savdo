package uz.bdmgroup.barakasavdo.productDetil

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_product_detail.*
import uz.bdmgroup.barakasavdo.Utils.LocaleManager
import uz.bdmgroup.barakasavdo.R
import uz.bdmgroup.barakasavdo.Utils.Constants
import uz.bdmgroup.barakasavdo.Utils.PrefUtils
import uz.bdmgroup.barakasavdo.model.ProductModel

class ProductDetailActivity : AppCompatActivity() {
    lateinit var item:ProductModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_detail)

        item=intent.getSerializableExtra("go_product")as ProductModel

        cardviewBack.setOnClickListener(){
            finish()
        }
        cardviewFavorite.setOnClickListener(){
            PrefUtils.setFavorite(item)
            if (PrefUtils.checkedFavorite(item)){
                imageFavorite.setImageResource(R.drawable.ic_baseline_favorite_24)
            }else{
                imageFavorite.setImageResource(R.drawable.ic_baseline_favorite_border_24)
            }
        }

        //oldin favorite tanlangan bolsa yurakcha ikonkani tola yurakchali ikonka bn almashtirish
        if (PrefUtils.checkedFavorite(item)){
            imageFavorite.setImageResource(R.drawable.ic_baseline_favorite_24)
        }else{
            imageFavorite.setImageResource(R.drawable.ic_baseline_favorite_border_24)
        }

        //oldin qo'shilgan bolsa korzinaga btnCart knopkasini ko'rinmas qilish
        if (PrefUtils.getCartCount(item)>0){
            btnAddToCart.visibility=View.GONE
        }

        //btnCart ni bossa korzinkaga qo'shib olish
        btnAddToCart.setOnClickListener(){
            item.cartCount=1
            PrefUtils.setCart(item)
            Toast.makeText(this, "Mahsulot Savatga qo'shildi", Toast.LENGTH_LONG).show()
            finish()
        }

        ProductName.text=item.name
        ProductPrice.text=item.price
        Glide.with(this).load(Constants.HOSTING_IMAGE+item.image).into(ProductImage)
    }

    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(LocaleManager.setLocale(newBase))
    }
}