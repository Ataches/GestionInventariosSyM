package com.example.stockmanagementsym.logic.adapter.photo

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.core.app.ActivityCompat.startActivityForResult

class GalleryAdaptee {
    fun startGallery(activity: Activity,context:Context){
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        if(intent.resolveActivity(context.packageManager) != null){
            activity.startActivityForResult(intent, 101)
        }
    }
}