package uz.bdmgroup.barakasavdo.api

import io.reactivex.Observable
import retrofit2.Call
import retrofit2.http.*
import uz.bdmgroup.barakasavdo.model.*
import uz.bdmgroup.barakasavdo.model.Request.GetProductByIdByRequest
import uz.bdmgroup.barakasavdo.model.Request.MakeOrderRequest
import uz.bdmgroup.barakasavdo.model.Request.RegisterRequest

interface Api{
    @GET("get_offers")
    fun getOffers():Observable<BaseResponse<List<OfferModel>>>

    @GET("get_categories")
    fun  getCategories():Observable<BaseResponse<List<CategoryModel>>>

    @GET("get_top_products")
    fun getTopProducts():Observable<BaseResponse<List<ProductModel>>>

    @GET("get_products/{category_id}")
    fun getProductsByCategory(@Path("category_id")category_Id:Int):Observable<BaseResponse<List<ProductModel>>>

    @POST("get_products_by_ids")
    fun getProductsByIdRequest(@Body request: GetProductByIdByRequest):Observable<BaseResponse<List<ProductModel>>>

    //autotentifikatsiya uchun
    @GET("check_phone")
    fun checkPhone(@Query("phone") phone: String): Observable<BaseResponse<CheckPhoneResponse>>

    @GET("login")
    fun login(@Query("phone") phone: String, @Query("password") password: String): Observable<BaseResponse<LoginResponse>>

    @POST("register")
    fun register(@Body request: RegisterRequest): Observable<BaseResponse<Any>>

    @GET("confirm")
    fun confirm(@Query("phone") phone: String, @Query("sms_code") password: String): Observable<BaseResponse<LoginResponse>>

    @POST("make_order")
    fun makeOrder(@Body request: MakeOrderRequest): Observable<BaseResponse<Any>>
}