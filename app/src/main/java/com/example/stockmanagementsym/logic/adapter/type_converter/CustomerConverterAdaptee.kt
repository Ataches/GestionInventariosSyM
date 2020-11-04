package com.example.stockmanagementsym.logic.adapter.type_converter

import androidx.room.TypeConverter
import com.example.stockmanagementsym.data.CONSTANTS
import com.example.stockmanagementsym.logic.business.Customer

class CustomerConverterAdaptee {

    @TypeConverter
    fun customerToStoredString(customer: Customer):String{
        return customer.getName()+CONSTANTS.STORED_STRING_ITEM_LIMITER+
                customer.getAddress()+CONSTANTS.STORED_STRING_ITEM_LIMITER+
                customer.getPhone()+CONSTANTS.STORED_STRING_ITEM_LIMITER+
                customer.getCity()
    }
    @TypeConverter
    fun storedStringToCustomer(string: String): Customer? {
        val dataCustomer = string.split(CONSTANTS.STORED_STRING_ITEM_LIMITER)
        if(dataCustomer.isNotEmpty())
            return Customer(
                name = dataCustomer[0],
                address = dataCustomer[1],
                phone = dataCustomer[2],
                city = dataCustomer[3]
            )
        return null
    }

    fun customerToString(customer: Customer):String{
        return  "Nombre: "+customer.getName()+ CONSTANTS.STRING_NEW_LINE+
                "Direccion: "+customer.getAddress()+ CONSTANTS.STRING_NEW_LINE+
                "Telefono: "+customer.getPhone()+ CONSTANTS.STRING_NEW_LINE+
                "Ciudad: "+customer.getCity()+ CONSTANTS.STRING_NEW_LINE
    }
}