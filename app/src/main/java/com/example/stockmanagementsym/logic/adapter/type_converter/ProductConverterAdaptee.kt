package com.example.stockmanagementsym.logic.adapter.type_converter

import com.example.stockmanagementsym.data.CONSTANTS
import com.example.stockmanagementsym.logic.business.Product
import java.text.DecimalFormat

class ProductConverterAdaptee(private var stringToUserBoolean: Boolean) {


    fun productListToStoredString(productList:MutableList<Product>):String{
        if(stringToUserBoolean)
            return productListToString(productList)
        var value = ""
        for (product in productList){
            value+= product.getName()+CONSTANTS.STORED_STRING_ITEM_LIMITER+
                    product.getPrice()+CONSTANTS.STORED_STRING_ITEM_LIMITER+
                    product.getDescription()+CONSTANTS.STORED_STRING_ITEM_LIMITER+
                    product.getQuantity()+CONSTANTS.STORED_STRING_ITEM_LIMITER+
                    CONSTANTS.STRING_VOID_ELEMENT+CONSTANTS.STORED_STRING_NEW_LINE //product.getStringBitMap()+CONSTANTS.STRING_ITEM_LIMITER+ //Data not needed to store, if you need the image info use this line
        }
        return value
    }

    fun storedStringToProductList(value: String): MutableList<Product>{
        var dataProducts = value.split(CONSTANTS.STORED_STRING_NEW_LINE)
        dataProducts = dataProducts.filter{it != ""}//Remove void elements in list
        val productList: MutableList<Product> = mutableListOf()
        if(dataProducts.isNotEmpty())
            for(dataProduct in dataProducts){
                val data = dataProduct.split(CONSTANTS.STORED_STRING_ITEM_LIMITER)
                val product = Product(
                        name = data[0],
                        price = data[1].toDouble(),
                        description = data[2],
                        quantity = data[3].toInt(),
                        stringBitMap = data[4]
                )
                productList.add(product)
            }
        return productList
    }

    fun setBooleanStringToUser(stringToUser:Boolean){
        this.stringToUserBoolean = stringToUser
    }

    //ProductList
    private fun productListToString(productList:MutableList<Product>):String{
        stringToUserBoolean = false
        var value = ""
        for (product in productList)
            value+= productToString(product)
        return value
    }

    //Product
    fun productToString(product: Product):String{
        val df = DecimalFormat("$###,###,###")
        return "Nombre: "+product.getName()+CONSTANTS.STRING_NEW_LINE+
                "Precio: "+df.format(product.getPrice())+CONSTANTS.STRING_NEW_LINE+
                "Descripción: "+product.getDescription()+CONSTANTS.STRING_NEW_LINE+
                "Cantidad: "+product.getQuantity()+CONSTANTS.STRING_NEW_LINE+CONSTANTS.STRING_NEW_LINE
    }
}