package uz.bdmgroup.barakasavdo.api

import io.reactivex.Observable
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import uz.bdmgroup.barakasavdo.model.BaseResponse
import uz.bdmgroup.barakasavdo.model.CategoryModel
import uz.bdmgroup.barakasavdo.model.OfferModel
import uz.bdmgroup.barakasavdo.model.ProductModel
import uz.bdmgroup.barakasavdo.model.Request.GetProductByIdByRequest

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
}