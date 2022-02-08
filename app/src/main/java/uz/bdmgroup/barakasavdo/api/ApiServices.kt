package uz.bdmgroup.barakasavdo.api

import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object ApiServices{
   var retrofit:Retrofit?=null
    var api:Api?=null

           fun ApiCreator():Api{
               if (retrofit==null){
                   retrofit=Retrofit.Builder()
                       .addConverterFactory(GsonConverterFactory.create())
                       .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                       .baseUrl("http://osonsavdo.devapp.uz/api/")
                       .build()
                   api= retrofit!!.create(Api::class.java)
               }
               return api!!
           }
}