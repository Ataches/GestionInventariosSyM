package com.example.stockmanagementsym.data.network.apis

import com.example.stockmanagementsym.data.network.request.RequestREST
import com.example.stockmanagementsym.data.network.response.ResponseREST
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface APIService {

    @POST("/location/value")
    fun petition(@Body data:RequestREST): Call<ResponseREST>
}