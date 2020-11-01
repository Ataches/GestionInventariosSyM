package com.example.stockmanagementsym.logic.adapter.photo

import android.content.Context

interface IPhotoAdapter {
    fun startCamera(context:Context)
    fun startGallery(context:Context)
}