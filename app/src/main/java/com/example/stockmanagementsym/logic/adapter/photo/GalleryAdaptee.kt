package com.example.stockmanagementsym.logic.adapter.photo

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.core.app.ActivityCompat.startActivityForResult

class GalleryAdaptee {
    fun startGallery(context:Context){
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        if(intent.resolveActivity(context.packageManager) != null){
            (context as Activity).startActivityForResult(intent, 101)
        }
    }
}