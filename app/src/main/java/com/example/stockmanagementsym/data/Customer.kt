package com.example.stockmanagementsym.data

data class Customer(private val name: String, private val address: String, private val phone: String){
    private lateinit var customerList : List<Customer>
    fun getName():String{
        return name
    }
    fun getPhone():String{
        return phone
    }
    fun getAddress():String{
        return address
    }
    fun createCustomers():List<Customer>{
        customerList = listOf(
            Customer("Carlos Gutierrez", "Cr 9 #20 68", "3203232321"),
            Customer("Antonio Perez", "Cra 9 #30 60", "3001231231"),
            Customer("Miguel Velasquez", "Calle 6a #20 22", "3103828329")
        )
        return customerList
    }
}