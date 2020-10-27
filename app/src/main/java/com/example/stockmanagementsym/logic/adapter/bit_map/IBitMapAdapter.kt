package com.example.stockmanagementsym.logic.adapter.bit_map

import android.graphics.Bitmap

interface IBitMapAdapter {
    fun encoderBitMapToString(imageBitmap: Bitmap): String
    fun decoderStringToBitMap(string: String): Bitmap
}