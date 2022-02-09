package uz.bdmgroup.barakasavdo

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import uz.bdmgroup.barakasavdo.api.repository.ShopRepository
import uz.bdmgroup.barakasavdo.databaseDAO.AppDataBase
import uz.bdmgroup.barakasavdo.model.*

class MainViewModel : ViewModel() {

    val repository = ShopRepository()

    val error = MutableLiveData<String>()
    val offersdata = MutableLiveData<List<OfferModel>>()
    val categoriessData = MutableLiveData<List<CategoryModel>>()
    val topProductData = MutableLiveData<List<ProductModel>>()
    val progress = MutableLiveData<Boolean>()

    val checkPhoneData = MutableLiveData<CheckPhoneResponse>()
    val registrationData = MutableLiveData<Boolean>()
    val confirmData = MutableLiveData<LoginResponse>()
    val loginData = MutableLiveData<LoginResponse>()
    val makeOrderData = MutableLiveData<Boolean>()

    fun checkPhone(phone: String){
        repository.checkPhone(phone, error, progress, checkPhoneData)
    }

    fun registrationData(fullname: String, phone: String, password: String){
        repository.registration(fullname, phone, password, error, progress, registrationData)
    }

    fun login(phone: String, password: String){
        repository.login(phone, password, error, progress, loginData)
    }

    fun confirmUser(phone: String, code: String){
        repository.confirmUser(phone, code, error, progress, confirmData)
    }

    fun makeOrder(products: List<CartModel>, lat: Double, lon: Double, comment: String){
        repository.makeOrder(products, lat, lon, comment, error, progress, makeOrderData)
    }


    fun getOffers() {
        repository.getOffers(error, progress, offersdata)
    }

    fun getCategories() {
        repository.getCategoriesData(error, categoriessData)
    }

    fun getTopProduct() {
        repository.getTopProduct(error, topProductData)
    }

    fun getProductByCategories(id: Int) {
        repository.getProductByID(id, error, topProductData)
    }

    fun getProducstByIdsrequest(ids: List<Int>) {
        repository.getProductByIdRequest(ids, error, progress, topProductData)
    }

    //bu databse ga internetdan olib joylash uchun
    fun insertAllProducts2DB(items: List<ProductModel>) {
        CoroutineScope(Dispatchers.IO).launch {
            AppDataBase.getDataBAse().getProductDao().deleteAll()
            AppDataBase.getDataBAse().getProductDao().insertAll(items)

        }
    }

    fun insertAllCategories2DB(items: List<CategoryModel>) {
        CoroutineScope(Dispatchers.IO).launch {
            AppDataBase.getDataBAse().getCategoriesDao().deleteAll()
            AppDataBase.getDataBAse().getCategoriesDao().insertAll(items)

        }
    }

    //bu database dan dasturga chiqarish uchun
    fun getAllDBproducts() {
        CoroutineScope(Dispatchers.Main).launch {
            topProductData.value = withContext(Dispatchers.IO) {
            AppDataBase.getDataBAse().getProductDao().getAllProducts()
            }
        }
    }

    fun getAllDBCategories() {
        CoroutineScope(Dispatchers.Main).launch {
            categoriessData.value = withContext(Dispatchers.IO) {
                AppDataBase.getDataBAse().getCategoriesDao().getAllCategories()
            }
        }
    }

}