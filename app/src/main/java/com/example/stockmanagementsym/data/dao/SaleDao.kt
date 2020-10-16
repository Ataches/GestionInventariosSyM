package com.example.stockmanagementsym.data.dao

import androidx.room.*
import com.example.stockmanagementsym.logic.business.Sale

@Dao
interface SaleDao {

    @Insert
    fun insert(sale: Sale)

    @Delete
    fun delete(sale: Sale)

    @Query("SELECT * FROM SALE")
    fun select():List<Sale>

}

