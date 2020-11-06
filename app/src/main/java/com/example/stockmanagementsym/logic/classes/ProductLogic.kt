package com.example.stockmanagementsym.logic.classes

import com.example.stockmanagementsym.data.MESSAGES
import com.example.stockmanagementsym.logic.ProductRESTLogic
import com.example.stockmanagementsym.logic.business.Product
import com.example.stockmanagementsym.logic.list_manager.IListManager
import com.example.stockmanagementsym.presentation.fragment.ICart
import org.jetbrains.anko.doAsyncResult
import org.jetbrains.anko.uiThread

class ProductLogic : AbstractListLogic() {

    private var productRestLogic: ProductRESTLogic? = null
    private lateinit var iCart: ICart

    override fun loadData() {
        doAsyncResult {
            if (elementList.isEmpty()) {
                elementList = productLogicDao.selectProductList().toMutableList()
            }
            getRESTLogic().searchProductList()
            uiThread {
                iListManager?.reloadList(elementList.toMutableList())
                iCart.setProductList(elementList)
                elementList.addAll(getRESTLogic().getProductList())
            }
        }

    }

    override fun insert(element: Any) {
        doAsyncResult {
            try {
                productLogicDao.insert(element as Product)
                updateMutableList()
            } catch (e: Exception) {
                iListManager?.showResultTransaction(false)
            }
        }
    }

    override fun update(element: Any) {
        doAsyncResult {
            try {
                productLogicDao.update(element as Product)
                updateMutableList()
            } catch (e: Exception) {
                iListManager?.showResultTransaction(false)
            }
        }
    }

    override fun updateMutableList() {
        doAsyncResult {
            try {
                elementList = productLogicDao.selectProductList().toMutableList()
                elementList.addAll(getRESTLogic().getProductList())
                iCart.setProductList(elementList)
                uiThread {
                    notifyUserTransactionSuccess()
                }
            } catch (e: Exception) {
                iListManager?.showResultTransaction(false)
            }
        }
    }

    override fun delete(element: Any) {
        doAsyncResult {
            try {
                productLogicDao.delete(element as Product)
                updateMutableList()
            } catch (e: Exception) {
                iListManager?.showResultTransaction(false)
            }
        }
    }

    override fun searchTextInMutableList(searchedText: String) {
        val listSearched = (elementList as MutableList<Product>).filter {
            it.getName().toLowerCase().contains(searchedText.toLowerCase())
        }
        iListManager?.reloadList(listSearched.toMutableList())
    }

    override fun searchByIDInMutableList(id: Long): Product {
        return (elementList as MutableList<Product>).filter { it.idProduct == id }[0]
    }

    override fun setListManager(iListManager: IListManager) {
        this.iListManager = iListManager
    }

    override fun setCartListener(iCart: ICart) {
        this.iCart = iCart
    }

    override fun decreaseMutableListQuantity(mutableList: MutableList<Any>) {
        doAsyncResult {
            try {
                for (productInCart in mutableList as MutableList<Product>) {
                    val product = searchByIDInMutableList(productInCart.idProduct)
                    product.setQuantity(product.getQuantity() - productInCart.getQuantity())
                    productLogicDao.update(product)
                }
            } catch (e: Exception) {
                iListManager?.showToastMessage(MESSAGES.PRODUCT_LIST_UPDATE_FAILURE)
            }
            uiThread {
                iListManager?.showToastMessage(MESSAGES.PRODUCT_LIST_UPDATE_SUCCESS)
            }
        }
    }

    private fun getRESTLogic(): ProductRESTLogic {
        if (productRestLogic == null)
            productRestLogic = ProductRESTLogic(iListManager)
        return productRestLogic!!
    }
}