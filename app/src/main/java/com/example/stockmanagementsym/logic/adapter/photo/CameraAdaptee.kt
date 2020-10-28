package com.example.stockmanagementsym.logic.adapter.photo

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.provider.MediaStore

class CameraAdaptee {
    fun startCamera(activity: Activity,context:Context) {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if(intent.resolveActivity(context.packageManager) != null){
            activity.startActivityForResult(intent,10)
        }
    }
}