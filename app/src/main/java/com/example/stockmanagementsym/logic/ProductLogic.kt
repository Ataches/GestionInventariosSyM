package com.example.stockmanagementsym.logic

import com.example.stockmanagementsym.data.dao.ProductDao
import com.example.stockmanagementsym.logic.business.Product
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ProductLogic(private var productDao: ProductDao) {

    private var productList:List<Product> = listOf()

    suspend fun updateProduct(productToUpdate: Product):Boolean {
        return try{
            productDao.update(productToUpdate)
            updateProductList()
            true
        }catch (e:Exception){
            false
        }
    }

    suspend fun createProduct(newProduct:Product):Boolean{
        return try{
            productDao.insert(newProduct)
            updateProductList()
            true
        }catch (e:Exception){
            false
        }
    }

    suspend fun deleteProduct(product:Product):Boolean{
        return try{
            productDao.delete(product)
            updateProductList()
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
        if(productList.isEmpty()){
            GlobalScope.launch(Dispatchers.IO){
                productList = productDao.selectProductList()
            }
            delay(100)
        }
        return productList
    }

    private suspend fun updateProductList() {
        productList = productDao.selectProductList()
    }

}