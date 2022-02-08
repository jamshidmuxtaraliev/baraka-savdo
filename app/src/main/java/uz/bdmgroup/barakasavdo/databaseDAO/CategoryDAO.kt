package uz.bdmgroup.barakasavdo.databaseDAO

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import uz.bdmgroup.barakasavdo.model.CategoryModel

@Dao
interface CategoryDAO {
    @Query("DELETE from categories")
    fun deleteAll()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(items:List<CategoryModel>)

    @Query("SELECT*FROM categories")
    fun getAllCategories():List<CategoryModel>
}