<<<<<<< HEAD
package com.example.stockmanagementsym.logic

import android.util.Log
import com.example.stockmanagementsym.data.Product
=======
package com.example.stocmanagementsym.logic

import android.util.Log
import com.example.stocmanagementsym.data.Product
>>>>>>> temp

object CartObject {
    private var list:MutableList<Product> = mutableListOf()
    fun addProduct(item:Product):String{
        Log.d("PRUEBA", "Por ENTRA")

        for(itemList in list) {
            Log.d("PRUEBA", "Por aqui paso "+itemList.getNombre()+" "+item.getNombre())
            if (itemList.getNombre().equals(item.getNombre()))
                return "El producto ya está en el carrito"
        }
        list.add(item)
        return "Producto añadido al carrito"
    }
    fun getList():MutableList<Product>{
        return list
    }
}