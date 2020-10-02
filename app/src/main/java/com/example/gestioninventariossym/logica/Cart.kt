package com.example.gestioninventariossym.logica

import com.example.gestioninventariossym.datos.Product

object Cart {
    private var list:MutableList<Product> = mutableListOf()
    fun addProduct(item:Product):String{
        if(!list.contains(item)){
            list.add(item)
            return "Producto añadido al carrito"
        }else
            return "El producto ya está en el carrito"
    }
    fun getList():MutableList<Product>{
        return list
    }
}