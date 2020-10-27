package com.example.stockmanagementsym.logic.adapter.photo

import android.app.Activity
import android.content.Context

interface IPhotoAdapter {
    fun startCamera(activity: Activity,context:Context)
    fun startGallery(activity: Activity,context:Context)
}