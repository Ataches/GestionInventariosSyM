package com.example.stockmanagementsym.logic

import com.example.stockmanagementsym.R
import com.example.stockmanagementsym.data.network.RetrofitInstance
import com.example.stockmanagementsym.data.network.apis.APIService
import com.example.stockmanagementsym.logic.business.Product
import com.example.stockmanagementsym.logic.list_manager.IListManager
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class ProductRESTLogic(private val listener: IListManager) {
    private lateinit var productList: List<Product>
    fun searchProductList() {
        doAsync {
            val callRetrofit =
                    RetrofitInstance()
                            .getInstance()
                            .create(APIService::class.java)
                            .getProductList()
                            .execute()
            uiThread {
                if (callRetrofit.isSuccessful) {
                    productList = callRetrofit.body()?.productList?: listOf()
                    listener.addElementsToList(productList.toMutableList())
                    listener.showAlertMessage(R.string.titleProductListRestAdded,
                            R.string.messageProductListRestAdded)
                }else{
                    listener.showToastMessage(R.string.productListRestFailure)
                }
            }
        }
    }
    fun getProductList():List<Product>{
        return productList
    }
}
