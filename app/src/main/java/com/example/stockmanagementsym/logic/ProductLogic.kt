package com.example.stockmanagementsym.logic

import com.example.stockmanagementsym.data.Data
import com.example.stockmanagementsym.logic.business.Product

object ProductLogic {

    private lateinit var productEdited: Product
    private lateinit var productToEdit: Product
    private lateinit var newProduct:Product

    fun updateProduct():Boolean {
        return try{
            Data.updateProduct(productToEdit,productEdited)
            true
        }catch (e:Exception){
            false
        }
    }

    fun createNewProduct():Boolean{
        return try{
            Data.createProduct(newProduct)
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

    fun setNewProduct(newProduct: Product) {
        this.newProduct = newProduct
    }

    fun getNewProduct(): Product {
        return newProduct
    }

    fun searchProduct(searchText: String): List<Product> {
        return Data.getProductList().filter {product -> product.getName().toLowerCase().contains(searchText.toLowerCase())}
    }

}