package uz.bdmgroup.barakasavdo.model

data class BaseResponse<T>(
    val success:Boolean,
    val data:T,
    val message:String,
    val error_code:Int
)

