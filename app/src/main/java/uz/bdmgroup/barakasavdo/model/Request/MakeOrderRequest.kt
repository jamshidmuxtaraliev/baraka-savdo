package uz.bdmgroup.barakasavdo.model.Request

import uz.bdmgroup.barakasavdo.model.CartModel

data class MakeOrderRequest(
    val products:List<CartModel>,
    val order_type:String,
    val adress:String,
    val lat:String,
    val lon:String,
    val comment:String

)
