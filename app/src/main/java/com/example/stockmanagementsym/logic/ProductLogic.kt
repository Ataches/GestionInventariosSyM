package com.example.stockmanagementsym.logic

import com.example.stockmanagementsym.data.MESSAGES
import com.example.stockmanagementsym.data.dao.ProductDao
import com.example.stockmanagementsym.logic.business.Product
import com.example.stockmanagementsym.logic.list_manager.IListManager
import com.example.stockmanagementsym.presentation.fragment.ICart
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.doAsyncResult
import org.jetbrains.anko.uiThread

class ProductLogic(private var productDao: ProductDao, private var listManager: IListManager) {

    private var productRestLogic: ProductRESTLogic? = null

    private var productList: MutableList<Product> = mutableListOf()
    private var productListREST: List<Product> = listOf()

    private lateinit var iCart: ICart

    private var updateListBoolean = false

    fun loadData() {
        doAsyncResult {
            if(productList.isEmpty())
                productList = productDao.selectProductList()
            uiThread {
                listManager.reloadList(productList.toMutableList())
                iCart.setProductList(productList)
            }
        }
        doAsync {
            getRESTLogic().searchProductList()
            addProductListREST(getRESTLogic().getProductList())
        }
    }

    fun setCartListener(iCart: ICart) {
        this.iCart = iCart
    }

    fun createProduct(newProduct: Product) {
        doAsync {
            try {
                productDao.insert(newProduct)
                updateProductList()
                if (!updateListBoolean)
                    notifyUserTransactionSuccess()
            } catch (e: Exception) {
                listManager.showResultTransaction(false)
            }
        }
    }

    private fun searchProductByID(id: Long): Product {
        return productList.filter { it.idProduct == id }[0]
    }

    fun searchProduct(searchText: String): List<Product> {
        return productList.filter { it.getName().toLowerCase().contains(searchText.toLowerCase()) }
    }

    fun updateProduct(productToUpdate: Product) {
        doAsync {
            try {
                productDao.update(productToUpdate)
                updateProductList()
                notifyUserTransactionSuccess()
            } catch (e: Exception) {
                listManager.showResultTransaction(false)
            }
        }
    }

    fun deleteProduct(product: Product) {
        doAsync {
            try {
                productDao.delete(product)
                updateProductList()
                notifyUserTransactionSuccess()
            } catch (e: Exception) {
                listManager.showResultTransaction(false)
            }
        }
    }

    private fun addProductListREST(list: List<Product>) {
        productListREST = list
        productList.addAll(list)
    }

    private fun updateProductList() {
        productList = productDao.selectProductList()
        productList.addAll(productListREST)
        iCart.setProductList(productList)
    }

    fun decreaseProductListQuantity(productList: MutableList<Product>) {
        try {
            GlobalScope.launch(Dispatchers.IO) {
                updateListBoolean = true
                productList.forEach {
                    val product = searchProductByID(it.idProduct)
                    product.setQuantity(product.getQuantity() - it.getQuantity())
                    updateProduct(product)
                }
                listManager.showToastMessage(MESSAGES.PRODUCT_LIST_UPDATE_SUCCESS)
                updateListBoolean = false
            }
        } catch (e: Exception) {
            listManager.showToastMessage(MESSAGES.PRODUCT_LIST_UPDATE_FAILURE)
        }
    }

    private fun notifyUserTransactionSuccess() {
        listManager.reloadList(productList.toMutableList())
        listManager.showResultTransaction(true)
    }

    fun getProductList(): MutableList<Product> {
        return productList
    }

    private fun getRESTLogic(): ProductRESTLogic {
        if (productRestLogic == null)
            productRestLogic = ProductRESTLogic(listManager)
        return productRestLogic!!
    }
}