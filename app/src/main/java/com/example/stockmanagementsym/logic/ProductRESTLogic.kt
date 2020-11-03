package com.example.stockmanagementsym.logic

import com.example.stockmanagementsym.R
import com.example.stockmanagementsym.data.network.RetrofitInstance
import com.example.stockmanagementsym.data.network.apis.APIService
import com.example.stockmanagementsym.logic.business.Product
import com.example.stockmanagementsym.logic.list_manager.IListManager
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class ProductRESTLogic(private val listener: IListManager?) {
    private var productList: List<Product> = listOf()
    fun searchProductList() {
        if (productList.isEmpty())
            doAsync {
                val callRetrofit =
                    RetrofitInstance()
                        .getInstance()
                        .create(APIService::class.java)
                        .getProductList()
                        .execute()
                uiThread {
                    if (callRetrofit.isSuccessful) {
                        productList = callRetrofit.body()?.productList ?: listOf()
                        listener?.addElementsToList(productList.toMutableList())
                        listener?.showAlertMessage(R.string.titleProductListRestAdded,
                            R.string.messageProductListRestAdded)
                    }else{
                        listener?.showToastMessage(R.string.productListRestFailure)
                    }
                }
            }
    }

    // If you call this method is possible that the data didn't already charged.
    // You must call it two times if the data is empty or call it in a
    // coroutine that uses the context to wait before data loads.
    fun getProductList():List<Product>{
        return productList
    }
}
