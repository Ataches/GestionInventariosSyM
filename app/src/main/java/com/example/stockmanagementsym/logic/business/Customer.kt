package com.example.stockmanagementsym.logic.business

data class Customer(private var name: String, private var address: String, private var phone: String, private var city:String){
    //private val idcustomer: String = UUID.randomUUID().toString()

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