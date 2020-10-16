package com.example.stockmanagementsym.logic.business

import androidx.annotation.NonNull
import androidx.room.Entity
import java.util.*

@Entity(tableName = "customer",
    primaryKeys = ["idCustomer"])
data class Customer(
    @NonNull
    var idCustomer:String = UUID.randomUUID().toString(),
    private var name: String,
    private var address: String,
    private var phone: String,
    private var city:String
){

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