package com.example.stockmanagementsym.logic

import com.example.stockmanagementsym.R
import com.example.stockmanagementsym.data.network.RetrofitInstance
import com.example.stockmanagementsym.data.network.apis.APIService
import com.example.stockmanagementsym.presentation.AndroidModel
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class RESTLogic(private val androidModel: AndroidModel) {

    fun getProductList(){
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
                    androidModel.addProductListREST(productList)
                    androidModel.showAlertMessage(R.string.titleProductListRestAdded,
                                                    R.string.messageProductListRestAdded)
                }else{
                    androidModel.showToastMessage(callRetrofit.message())
                }
            }
        }
    }
}
