package com.example.stockmanagementsym.logic

import com.example.stockmanagementsym.data.dao.ProductDao
import com.example.stockmanagementsym.logic.business.Product

class ProductLogic(private var productDao: ProductDao) {

    private var productList:List<Product> ?= null

    fun updateProduct(productToUpdate: Product):Boolean {
        return try{
            productDao.update(productToUpdate)
            updateProductList()
            true
        }catch (e:Exception){
            false
        }
    }

    fun createProduct(newProduct:Product):Boolean{
        return try{
            productDao.insert(newProduct)
            updateProductList()
            true
        }catch (e:Exception){
            false
        }
    }

    fun deleteProduct(product:Product):Boolean{
        return try{
            productDao.delete(product)
            updateProductList()
            true
        }catch (e:Exception){
            false
        }
    }

    fun searchProduct(searchText: String): List<Product> {
        return getProductList().filter {product -> product.getName().toLowerCase().contains(searchText.toLowerCase())}
    }

    fun getProductList(): List<Product> {
        if(productList==null)
            productList = productDao.selectProductList()
        return productList!!
    }

    private fun updateProductList() {
        productList = productDao.selectProductList()
    }

}