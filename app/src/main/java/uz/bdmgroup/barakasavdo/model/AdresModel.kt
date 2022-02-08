package uz.bdmgroup.barakasavdo.model

import java.io.Serializable

data class AdresModel(
    val address:String,
    val latitude:Double,
    val longitude:Double
):Serializable
