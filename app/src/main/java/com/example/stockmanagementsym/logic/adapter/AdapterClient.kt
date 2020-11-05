package com.example.stockmanagementsym.logic.adapter

import android.content.Context
import android.graphics.Bitmap
import com.example.stockmanagementsym.LoginActivity
import com.example.stockmanagementsym.logic.adapter.bit_map.BitMapConcreteAdapter
import com.example.stockmanagementsym.logic.adapter.bit_map.IBitMapAdapter
import com.example.stockmanagementsym.logic.adapter.photo.IPhotoAdapter
import com.example.stockmanagementsym.logic.adapter.photo.PhotoConcreteAdapter
import com.example.stockmanagementsym.logic.adapter.type_converter.ITypeConverterAdapter
import com.example.stockmanagementsym.logic.adapter.type_converter.TypeConverterConcrete
import com.example.stockmanagementsym.logic.business.Customer
import com.example.stockmanagementsym.logic.business.Product
import com.example.stockmanagementsym.logic.business.User

/**
 * Created by Juan Sebastian Sanchez Mancilla on 3/11/2020
 * Intermediate class between a class that needs adapters and adapters
*/
class AdapterClient {

    //adapters
    private var photoConcreteAdapter: IPhotoAdapter? = null
    private var bitMapConcreteAdapter: IBitMapAdapter? = null
    private var typeConverterConcrete: ITypeConverterAdapter? = null

    fun setBooleanStringToUser(booleanStringToUser: Boolean) {
        getTypeConverterAdapter().setBooleanStringToUser(booleanStringToUser)
    }

    fun productListToString(productList: MutableList<Product>): String {
        return getTypeConverterAdapter().productListToString(productList)
    }

    fun customerToString(customer: Customer): String {
        return getTypeConverterAdapter().customerToString(customer)
    }

    fun productToString(product: Product): String {
        return getTypeConverterAdapter().productToString(product)
    }

    fun getUserToString(user: User): String {
        return getTypeConverterAdapter().getUserToString(user)
    }

    fun startCamera(context: Context) {
        getPhotoAdapter().startCamera(context)
    }

    fun startGallery(context: Context) {
        getPhotoAdapter().startGallery(context)
    }

    fun decoderStringToBitMap(string: String): Bitmap {
        return getBitMapAdapter().decoderStringToBitMap(string)
    }

    fun encoderBitMapToString(bitMap: Bitmap): String {
        return getBitMapAdapter().encoderBitMapToString(bitMap)
    }

    private fun getTypeConverterAdapter(): ITypeConverterAdapter {
        if (typeConverterConcrete == null)
            typeConverterConcrete = TypeConverterConcrete()
        return typeConverterConcrete!!
    }

    private fun getPhotoAdapter(): IPhotoAdapter {
        if(photoConcreteAdapter==null)
            photoConcreteAdapter = PhotoConcreteAdapter()
        return photoConcreteAdapter!!
    }

    private fun getBitMapAdapter(): IBitMapAdapter {
        if (bitMapConcreteAdapter == null)
            bitMapConcreteAdapter = BitMapConcreteAdapter()
        return bitMapConcreteAdapter!!
    }
}