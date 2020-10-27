package com.example.stockmanagementsym.logic.adapter.bit_map

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import java.io.ByteArrayOutputStream

class BitMapAdaptee {

    fun encoderBitMapToString(imageBitmap: Bitmap): String{
        val byteArrayOutputStream =  ByteArrayOutputStream()
        imageBitmap.compress(Bitmap.CompressFormat.PNG,100, byteArrayOutputStream)
        val byteArray = byteArrayOutputStream.toByteArray()
        return Base64.encodeToString(byteArray, Base64.DEFAULT)
    }

    fun decoderStringToBitMap(string: String): Bitmap {
        val byteArrayFromString = Base64.decode(string, Base64.DEFAULT)
        return BitmapFactory.decodeByteArray(byteArrayFromString,0,byteArrayFromString.size)
    }
}