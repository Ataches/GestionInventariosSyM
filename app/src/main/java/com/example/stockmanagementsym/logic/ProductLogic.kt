package com.example.stockmanagementsym.logic

import com.example.stockmanagementsym.data.dao.ProductDao
import com.example.stockmanagementsym.logic.business.Product

class ProductLogic(private var productDao: ProductDao) {

    private lateinit var productEdited: Product
    private lateinit var productToEdit: Product
    private var productList:List<Product> ?= null

    fun updateProduct():Boolean {
        return try{
            productDao.update(productEdited)
            updateProductList()
            true
        }catch (e:Exception){
            false
        }
    }

    fun createNewProduct(newProduct:Product):Boolean{
        return try{
            productDao.insert(newProduct)
            updateProductList()
            true
        }catch (e:Exception){
            false
        }
    }

    fun setProductToEdit(customerToEdit : Product) {
        this.productToEdit = customerToEdit
    }

    fun setProductEdited(customerEdited: Product) {
        this.productEdited = customerEdited
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