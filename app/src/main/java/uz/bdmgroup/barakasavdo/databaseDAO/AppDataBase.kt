package uz.bdmgroup.barakasavdo.databaseDAO

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import uz.bdmgroup.barakasavdo.model.CategoryModel
import uz.bdmgroup.barakasavdo.model.ProductModel

@Database(entities = [ProductModel::class, CategoryModel::class], version = 1)
abstract class AppDataBase :RoomDatabase(){
    abstract fun getProductDao():ProductDAO
    abstract fun getCategoriesDao():CategoryDAO
    companion object{
        var INSTANCE:AppDataBase?=null

        fun initDataBase(context: Context){
            if (INSTANCE==null){
                synchronized(AppDataBase::class){
                    INSTANCE=Room.databaseBuilder(context.applicationContext, AppDataBase::class.java, "baraka_savdo").build()
                }
            }
        }
        fun getDataBAse():AppDataBase{
            return INSTANCE!!
        }
    }
}