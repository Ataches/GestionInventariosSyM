package com.example.stockmanagementsym.data

import com.example.stockmanagementsym.logic.business.Product

object CartObject {
    private var list:MutableList<Product> = mutableListOf()
    private lateinit var listSearched:List<Product>

    fun addProduct(item: Product):String{
        listSearched = list.filter{
            product -> product.getName() == item.getName()
        }
        if(listSearched.isNotEmpty())
            return "El producto ya está en el carrito"
        list.add(item)
        return "Producto añadido al carrito"
    }
    fun getList():MutableList<Product>{
        return list
    }
    fun getTotalPrice():Int{
        if(list.isNullOrEmpty())
            return 0
        return list.map{it.getPrice()*it.getQuantity()}.reduce{ acc, it -> acc + it}
    }

    fun clearCart() {
        list = mutableListOf()
    }
}