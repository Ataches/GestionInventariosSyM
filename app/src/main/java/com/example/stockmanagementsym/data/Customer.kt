package com.example.stockmanagementsym.data

data class Customer(private var name: String, private var address: String, private var phone: String, private var city:String){
    fun getName():String{
        return name
    }
    fun getPhone():String{
        return phone
    }
    fun getAddress():String{
        return address
    }
    fun getCity():String{
        return city
    }
}