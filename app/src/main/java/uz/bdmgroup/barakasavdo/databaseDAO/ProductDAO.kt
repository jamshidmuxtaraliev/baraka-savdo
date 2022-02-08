package uz.bdmgroup.barakasavdo.databaseDAO

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import uz.bdmgroup.barakasavdo.model.ProductModel
@Dao
interface ProductDAO{
   @Query("DELETE from products")
   fun deleteAll()

   @Insert(onConflict = OnConflictStrategy.REPLACE)
   fun insertAll(items:List<ProductModel>)

   @Query("SELECT*FROM  products")
   fun getAllProducts():List<ProductModel>
}