package com.example.stockmanagementsym.data.network.response

import com.example.stockmanagementsym.logic.business.Product
import com.google.gson.annotations.SerializedName

data class ProductListResponse (
    @SerializedName("products") val productList: List<Product>
)