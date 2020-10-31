package com.example.stockmanagementsym.logic

import com.example.stockmanagementsym.data.dao.ProductDao
import com.example.stockmanagementsym.logic.business.Product
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ProductLogic(private var productDao: ProductDao) {

    private var productListREST: MutableList<Product> = mutableListOf()
    private var productList: MutableList<Product> = mutableListOf()

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
        return getProductList().filter { it.idProduct == id }[0]
    }

    suspend fun searchProduct(searchText: String): List<Product> {
        return getProductList().filter { it.getName().toLowerCase().contains(searchText.toLowerCase())}
    }

    suspend fun getProductList(): MutableList<Product> {
        withContext(Dispatchers.IO) {
            if(productList.isEmpty()){
                productList = productDao.selectProductList()
            }
        }
        return productList
    }
    
    fun addProductListREST(mutableList: MutableList<Product>) {
        productListREST = mutableList
        productList.addAll(mutableList)
    }

    private suspend fun updateProductList() {
        productList = productDao.selectProductList()
        productList.addAll(productListREST)
    }
}