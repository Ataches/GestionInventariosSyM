package com.example.stockmanagementsym.logic.adapter.bit_map

import android.graphics.Bitmap

class BitMapConcreteAdapter:IBitMapAdapter {

    private var bitMapAdaptee: BitMapAdaptee ?= null

    override fun encoderBitMapToString(imageBitmap: Bitmap):String{
        return getBitMapAdaptee().encoderBitMapToString(imageBitmap)
    }

    override fun decoderStringToBitMap(string: String): Bitmap {
        return getBitMapAdaptee().decoderStringToBitMap(string)
    }

    private fun getBitMapAdaptee(): BitMapAdaptee {
        if(bitMapAdaptee==null)
            bitMapAdaptee = BitMapAdaptee()
        return bitMapAdaptee!!
    }
}