package com.example.stockmanagementsym.logic.adapter.photo

import android.content.Context

class PhotoConcreteAdapter : IPhotoAdapter {

    private var galleryAdaptee: GalleryAdaptee ?= null
    private var cameraAdaptee: CameraAdaptee ?= null

    override fun startCamera(context:Context) {
        getCameraAdaptee().startCamera(context)
    }

    override fun startGallery(context:Context){
        getGalleryAdaptee().startGallery(context)
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