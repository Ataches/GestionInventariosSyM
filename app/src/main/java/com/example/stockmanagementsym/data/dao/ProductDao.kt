package com.example.stockmanagementsym.data.dao

import androidx.room.*
import com.example.stockmanagementsym.logic.business.Product

@Dao
interface ProductDao {

    @Insert
    fun insert(product: Product)

    @Delete
    fun delete(product: Product)

    @Update
    fun update(productEdited: Product)

    @Query("SELECT * FROM PRODUCT")
    fun selectProductList():List<Product>

}

