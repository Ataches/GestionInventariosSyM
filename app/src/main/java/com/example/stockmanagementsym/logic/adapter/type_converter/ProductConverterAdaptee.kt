package com.example.stockmanagementsym.logic.adapter.type_converter

import android.util.Log
import com.example.stockmanagementsym.data.CONSTANTS
import com.example.stockmanagementsym.logic.business.Product
import java.text.DecimalFormat

class ProductConverterAdaptee(private var stringToUser: Boolean) {


    fun productListToStoredString(productList:MutableList<Product>):String{
        if(stringToUser)
            return productListToString(productList)
        var value = ""
        for (product in productList){
            value+= product.getName()+CONSTANTS.STRING_ITEM_LIMITER+
                    product.getPrice()+CONSTANTS.STRING_ITEM_LIMITER+
                    product.getDescription()+CONSTANTS.STRING_ITEM_LIMITER+
                    product.getStringBitMap()+CONSTANTS.STRING_ITEM_LIMITER+
                    product.getQuantity()+CONSTANTS.STRING_NEW_LINE
        }
        return value
    }

    fun storedStringToProductList(value: String): MutableList<Product>{
        var dataProducts = value.split(CONSTANTS.STRING_NEW_LINE)
        dataProducts = dataProducts.filter{it != ""}//Remove void elements in list
        val productList: MutableList<Product> = mutableListOf()
        if(dataProducts.isNotEmpty())
            for(dataProduct in dataProducts){
                Log.d("TEST DATA PROD LIST","PROD DATA STRING "+dataProduct)
                val data = dataProduct.split(CONSTANTS.STRING_ITEM_LIMITER)
                val product = Product(
                        name = data[0],
                        price = data[1].toDouble(),
                        description = data[2],
                        stringBitMap = data[3],
                        quantity = data[4].toInt()
                )
                productList.add(product)
            }
        return productList
    }

    fun setBooleanStringToUser(stringToUser:Boolean){
        this.stringToUser = stringToUser
    }

    //ProductList
    private fun productListToString(productList:MutableList<Product>):String{
        stringToUser = false
        var value = ""
        for (product in productList)
            value+= productToString(product)
        return value
    }

    //Product
    fun productToString(product: Product):String{
        val df = DecimalFormat("$###,###,###")
        return "Nombre: "+product.getName()+CONSTANTS.STRING_ITEM_LIMITER+
                "Precio: "+df.format(product.getPrice())+CONSTANTS.STRING_ITEM_LIMITER+
                "Descripción: "+product.getDescription()+CONSTANTS.STRING_ITEM_LIMITER+
                "Cantidad: "+product.getQuantity()+CONSTANTS.STRING_NEW_LINE
    }
}