package com.example.stockmanagementsym.logic

import android.util.Log
import com.example.stockmanagementsym.data.dao.ProductDao
import com.example.stockmanagementsym.logic.business.Product
import kotlinx.coroutines.*

class ProductLogic(private var productDao: ProductDao) {

    private var productList:List<Product> = listOf()

    suspend fun updateProduct(productToUpdate: Product):Boolean {
        return try{
            withContext(Dispatchers.IO) {
                productDao.update(productToUpdate)
                updateProductList()
            }
            true
        }catch (e:Exception){
            false
        }
    }

    suspend fun createProduct(newProduct:Product):Boolean{
        return try{
            withContext(Dispatchers.IO) {
                productDao.insert(newProduct)
                updateProductList()
            }
            true
        }catch (e:Exception){
            false
        }
    }

    suspend fun deleteProduct(product:Product):Boolean{
        return try{
            withContext(Dispatchers.IO) {
                productDao.delete(product)
                updateProductList()
            }
            true
        }catch (e:Exception){
            false
        }
    }

    suspend fun searchProducts(id:Long): Product {
        return getProductList().filter { product -> product.idProduct == id }[0]
    }

    suspend fun searchProduct(searchText: String): List<Product> {
        return getProductList().filter { product -> product.getName().toLowerCase().contains(searchText.toLowerCase())}
    }

    suspend fun getProductList(): List<Product> {
        withContext(Dispatchers.IO) {
            if(productList.isEmpty()){
                productList = productDao.selectProductList()
            }
        }
        return productList
    }

    private suspend fun updateProductList() {
        productList = productDao.selectProductList()
    }

    //TypeConverter
    //ProductList
    fun productListToString(productList:MutableList<Product>,toDB: Boolean):String{
        var value = ""
        for (product in productList)
            value+= productToString(product,toDB)
        return value
    }

    fun storedStringToProductList(value: String, toDB:Boolean): MutableList<Product>{
        var dataProducts = value.split("\n\n")
        dataProducts = dataProducts.filter{it != ""}//Remove void elements in list
        val productList: MutableList<Product> = mutableListOf()
        for(dataProduct in dataProducts)
            if(dataProducts.isNotEmpty())
                productList.add(storedStringToProduct(dataProduct,toDB))
        return productList
    }
    //Product
    fun productToString(product: Product, toDB: Boolean):String{
        return if(toDB)
            "Nombre: "+product.getName()+"\n"+
            "Precio: "+product.getPrice()+"\n"+
            "Descripción: "+product.getDescription()+"\n"+
            "bitMap: "+product.getStringBitMap()+"\n"+
            "Cantidad: "+product.getQuantity()+"\n\n"
        else
            "Nombre: "+product.getName()+"\n"+
            "Precio: "+product.getPrice()+"\n"+
            "Descripción: "+product.getDescription()+"\n"+
            "Cantidad: "+product.getQuantity()+"\n\n"
    }

    fun storedStringToProduct(string: String, toDB: Boolean): Product{
        var listString = string.split("\n")
        val bitmap:String
        val quantity:Int

        listString = listString.map { it.removePrefix("Nombre: ") }
        listString = listString.map { it.removePrefix("Precio: ") }
        listString = listString.map { it.removePrefix("Descripción: ") }

        if(toDB) {
            listString = listString.map { it.removePrefix("bitMap: ") }
            listString = listString.map { it.removePrefix("Cantidad: ") }

            bitmap = listString[3]
            quantity = listString[4].toInt()
        }
        else{
            listString = listString.map { it.removePrefix("Cantidad: ") }

            bitmap = ""
            quantity = listString[3].toInt()
        }

        return Product(
                    name = listString[0],
                    price = listString[1].toInt(),
                    description = listString[2],
                    stringBitMap = bitmap,
                    quantity = quantity
                )
    }
}