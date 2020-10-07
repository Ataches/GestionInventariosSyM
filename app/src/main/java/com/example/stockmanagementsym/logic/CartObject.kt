package com.example.stockmanagementsym.logic

import com.example.stockmanagementsym.data.Product

object CartObject {
    private var list:MutableList<Product> = mutableListOf()
    private lateinit var listSearched:List<Product>

    fun addProduct(item:Product):String{
        if(item.getQuantity()==0) item.setQuantity(1)
        listSearched = list.filter{
            product -> product.getName().equals(item.getName())
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
        return list.map{it.getPrice()*it.getQuantity()}.reduce{acc, it -> acc + it}
    }
}