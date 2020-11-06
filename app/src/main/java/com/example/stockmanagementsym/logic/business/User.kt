package com.example.stockmanagementsym.logic.business

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.stockmanagementsym.data.CONSTANTS

@Entity
class User(
    private var userIdentification:String,
    private var name: String,
    private var password: String,
    private var privilege: String,
    private val photoData: String,
    private var latitude: Double,
    private var longitude: Double
){
    @PrimaryKey(autoGenerate = true)
    @NonNull @ColumnInfo(name = "id_user")
    var idSale:Long = CONSTANTS.ID_PRODUCT_DEFAULT

    fun getUserIdentification():String{
        return userIdentification
    }

    fun getName():String{
        return name
    }

    fun getPassword(): String {
        return password
    }

    fun getPrivilege(): String {
        return privilege
    }

    fun getPhotoData(): String {
        return photoData
    }

    fun setLatitude(latitude: Double) {
        this.latitude = latitude
    }

    fun getLatitude(): Double {
        return latitude
    }

    fun setLongitude(longitude: Double) {
        this.longitude = longitude
    }

    fun getLongitude(): Double {
        return longitude
    }
}