package com.example.stockmanagementsym.logic

import android.content.Context
import com.example.stockmanagementsym.data.network.RetrofitInstance
import com.example.stockmanagementsym.data.network.apis.APIService
import com.example.stockmanagementsym.data.network.request.RequestREST
import com.example.stockmanagementsym.presentation.AndroidModel
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class RESTLogic(private val androidModel: AndroidModel) {

    fun getData(context: Context){
        val dataToConfirmInServer=""
        doAsync{

            val requestREST = RequestREST(dataToConfirmInServer)
            val callRetrofit =
                RetrofitInstance().getInstance()
                    .create(APIService::class.java)
                    .petition(requestREST)
                    .execute()

            uiThread{
                if(callRetrofit.isSuccessful){
                    androidModel.showToastMessage(context,callRetrofit.message())
                }else{
                    androidModel.showToastMessage(context,callRetrofit.message())
                }
            }
        }
    }


}
