package com.example.stockmanagementsym.logic

import android.view.View
import com.example.stockmanagementsym.data.network.RetrofitInstance
import com.example.stockmanagementsym.data.network.apis.APIService
import com.example.stockmanagementsym.presentation.AndroidModel
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class RESTLogic(private val androidModel: AndroidModel) {

    fun getProductList(view: View){
        doAsync{
            val callRetrofit =
                RetrofitInstance()
                    .getInstance()
                    .create(APIService::class.java)
                    .getProductList()
                    .execute()
            uiThread{
                if(callRetrofit.isSuccessful){
                    val productList = callRetrofit.body()?.productList?: listOf()
                    androidModel.addProductsToProductList(productList, view)
                }else{
                    androidModel.showToastMessage(view.context,callRetrofit.message())
                }
            }
        }
    }
}
