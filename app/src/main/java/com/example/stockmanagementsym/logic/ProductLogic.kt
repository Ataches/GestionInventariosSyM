package com.example.stockmanagementsym.logic

import com.example.stockmanagementsym.data.Data
import com.example.stockmanagementsym.logic.business.Product


class ProductLogic {

    private lateinit var productEdited: Product
    private lateinit var productToEdit: Product

    fun editProduct():Boolean {
        return try{
            Data.updateProduct(productToEdit,productEdited)
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
}