package com.example.stockmanagementsym.logic.adapter.photo

import android.app.Activity
import android.content.Context

class PhotoConcreteAdapter : IPhotoAdapter {

    private var galleryAdaptee: GalleryAdaptee ?= null
    private var cameraAdaptee: CameraAdaptee ?= null

    override fun startCamera(activity: Activity,context:Context) {
        getCameraAdaptee().startCamera(activity,context)
    }

    override fun startGallery(activity: Activity, context:Context){
        getGalleryAdaptee().startGallery(activity,context)
    }

    private fun getCameraAdaptee():CameraAdaptee{
        if(cameraAdaptee==null)
            cameraAdaptee = CameraAdaptee()
        return cameraAdaptee!!
    }

    private fun getGalleryAdaptee():GalleryAdaptee{
        if(galleryAdaptee==null)
            galleryAdaptee= GalleryAdaptee()
        return galleryAdaptee!!
    }
}