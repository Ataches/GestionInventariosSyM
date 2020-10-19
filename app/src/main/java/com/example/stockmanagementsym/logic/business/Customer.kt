package com.example.stockmanagementsym.logic.business

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "customer")
data class Customer(
    private var name: String,
    private var address: String,
    private var phone: String,
    private var city:String
){
    @PrimaryKey(autoGenerate = true)
    @NonNull @ColumnInfo(name = "id_customer")
    var idCustomer:Long = 0L

    fun getName():String{
        return name
    }
    fun setName(name:String){
        this.name = name
    }

    fun getPhone():String{
        return phone
    }
    fun setPhone(phone: String){
        this.phone = phone
    }

    fun getAddress():String{
        return address
    }
    fun setAddress(address: String){
        this.address = address
    }

    fun getCity():String{
        return city
    }
    fun setCity(city: String){
        this.city = city
    }
}