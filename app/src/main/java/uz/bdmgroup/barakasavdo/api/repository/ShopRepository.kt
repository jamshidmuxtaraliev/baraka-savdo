package uz.bdmgroup.barakasavdo.api.repository

import androidx.lifecycle.MutableLiveData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import uz.bdmgroup.barakasavdo.Utils.PrefUtils
import uz.bdmgroup.barakasavdo.api.ApiServices
import uz.bdmgroup.barakasavdo.model.BaseResponse
import uz.bdmgroup.barakasavdo.model.CategoryModel
import uz.bdmgroup.barakasavdo.model.OfferModel
import uz.bdmgroup.barakasavdo.model.ProductModel
import uz.bdmgroup.barakasavdo.model.Request.GetProductByIdByRequest

class ShopRepository() {
    val compositeDisposable=CompositeDisposable()

    fun  getOffers(error:MutableLiveData<String>, progress:MutableLiveData<Boolean>, success:MutableLiveData<List<OfferModel>>){
        progress.value=true
        compositeDisposable.add(
            ApiServices.ApiCreator().getOffers()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableObserver<BaseResponse<List<OfferModel>>>(){
                    override fun onNext(t: BaseResponse<List<OfferModel>>) {
                        progress.value=false
                        if (t.success){
                            success.value=t.data
                        }
                        else{
                            error.value=t.message
                        }
                    }

                    override fun onError(e: Throwable) {
                       progress.value=false
                        error.value=e.localizedMessage
                    }

                    override fun onComplete() {

                    }
                })
        )

    }
    fun getCategoriesData(error: MutableLiveData<String>, success: MutableLiveData<List<CategoryModel>>){
     compositeDisposable.add(
         ApiServices.ApiCreator().getCategories()
             .subscribeOn(Schedulers.io())
             .observeOn(AndroidSchedulers.mainThread())
             .subscribeWith(object : DisposableObserver<BaseResponse<List<CategoryModel>>>(){
                 override fun onNext(t: BaseResponse<List<CategoryModel>>) {
                     if(t.success){
                         success.value=t.data
                     }else{
                         error.value=t.message
                     }
                 }
                 override fun onError(e: Throwable) {
                     error.value=e.localizedMessage
                 }
                 override fun onComplete() {

                 }
             })
     )
    }
    fun getTopProduct(error: MutableLiveData<String>, success: MutableLiveData<List<ProductModel>>){
        compositeDisposable.add(
            ApiServices.ApiCreator().getTopProducts()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object :DisposableObserver<BaseResponse<List<ProductModel>>>(){
                    override fun onNext(t: BaseResponse<List<ProductModel>>) {
                        if (t.success){
                            success.value=t.data
                        }else{
                            error.value=t.message
                        }
                    }
                    override fun onError(e: Throwable) {
                      error.value=e.localizedMessage
                    }

                    override fun onComplete() {

                    }
                })
        )
    }
    fun getProductByID(id:Int, error: MutableLiveData<String>, success: MutableLiveData<List<ProductModel>>){
        compositeDisposable.add(
            ApiServices.ApiCreator().getProductsByCategory(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object :DisposableObserver<BaseResponse<List<ProductModel>>>(){
                    override fun onNext(t: BaseResponse<List<ProductModel>>) {
                        if (t.success){
                            success.value=t.data
                        }
                    }

                    override fun onError(e: Throwable) {
                       error.value=e.localizedMessage
                    }

                    override fun onComplete() {

                    }
                })
        )

    }

    fun getProductByIdRequest(ids:List<Int>, error: MutableLiveData<String>, progress: MutableLiveData<Boolean>, success: MutableLiveData<List<ProductModel>>){
       progress.value=true
        compositeDisposable.add(
            ApiServices.ApiCreator().getProductsByIdRequest(GetProductByIdByRequest(ids))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object :DisposableObserver<BaseResponse<List<ProductModel>>>(){
                    override fun onNext(t: BaseResponse<List<ProductModel>>) {
                        progress.value=false
                        if (t.success){

                            PrefUtils.getCartList().forEach { cartProduct ->
                                t.data.forEach {
                                    if (cartProduct.product_id == it.id) {
                                        it.cartCount = cartProduct.count
                                    }
                                }
                            }

                            success.value=t.data
                        }
                        else{
                            error.value=t.message
                        }
                    }

                    override fun onError(e: Throwable) {
                        progress.value=false
                        error.value=e.localizedMessage
                    }

                    override fun onComplete() {

                    }
                })
        )

    }
    }