package uz.bdmgroup.barakasavdo.Utils

import com.orhanobut.hawk.Hawk
import uz.bdmgroup.barakasavdo.model.CartModel
import uz.bdmgroup.barakasavdo.model.ProductModel
import java.util.*

object PrefUtils {
    const val KEY_FAVORITE = "key_favorite"
    const val KEY_CART = "key_Cart"
    const val KEY_FCMTOKEN = "key_Fcm_token"

    fun setFavorite(item: ProductModel) {
        val items = Hawk.get(KEY_FAVORITE, arrayListOf<Int>())
        if (items.filter { it == item.id }.firstOrNull() != null) {
            items.remove(item.id)
        } else {
            items.add(item.id)
        }
        Hawk.put(KEY_FAVORITE, items)
    }


    fun getFavoriteList(): ArrayList<Int> {
        return Hawk.get(KEY_FAVORITE, arrayListOf<Int>())
    }


    fun checkedFavorite(item: ProductModel): Boolean {
        val items = Hawk.get(KEY_FAVORITE, arrayListOf<Int>())
        return items.filter { it == item.id }.firstOrNull() != null
    }


    fun setCart(item: ProductModel) {
        val items = Hawk.get<ArrayList<CartModel>>(KEY_CART, arrayListOf<CartModel>())
        val cart = items.filter { it.product_id == item.id }.firstOrNull()
        if (cart != null) {
            if (item.cartCount > 0) {
                cart.count = item.cartCount
            } else {
                items.remove(cart)
            }
        } else {
            val newCart = CartModel(item.id, item.cartCount)
            items.add(newCart)
        }
        Hawk.put(KEY_CART, items)
    }


    fun getCartList(): ArrayList<CartModel> {
        return Hawk.get(KEY_CART, arrayListOf<CartModel>())
    }


    fun getCartCount(item: ProductModel): Int {
        val items = Hawk.get<ArrayList<CartModel>>(KEY_CART, arrayListOf<CartModel>())
        return items.filter { it.product_id == item.id }.firstOrNull()?.count ?: 0
    }

    //firebasdan token olib saqlash
    fun setFCMToken(value:String){
        Hawk.put(KEY_FCMTOKEN, value)
    }

    //firebasdagi tokenni qaytarish
    fun getFCMToken():String{
        return Hawk.get(KEY_FCMTOKEN, "")
    }
}