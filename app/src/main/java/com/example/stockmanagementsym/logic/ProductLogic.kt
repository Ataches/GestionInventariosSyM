package com.example.stockmanagementsym.logic

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

}