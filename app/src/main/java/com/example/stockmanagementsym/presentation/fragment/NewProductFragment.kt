package com.example.stockmanagementsym.presentation.fragment

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.stockmanagementsym.R
import com.example.stockmanagementsym.presentation.AndroidController
import com.example.stockmanagementsym.presentation.view.FragmentData
import kotlinx.android.synthetic.main.fragment_new_product.*

class NewProductFragment: Fragment(){

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        AndroidController.setFragmentTransaction(parentFragmentManager.beginTransaction())
        FragmentData.setIdFragment(this.id)

        buttonNewProductToHome.setOnClickListener (AndroidController)
        buttonNewProductToProductList.setOnClickListener (AndroidController)
        buttonNewProductToNewProduct.setOnClickListener (AndroidController)
        buttonNewProductToGallery.setOnClickListener(AndroidController)
        buttonNewProductToCamera.setOnClickListener(AndroidController)
    }

    override fun onResume() {
        super.onResume()
        AndroidController.setFragmentTransaction(parentFragmentManager.beginTransaction())
        FragmentData.setIdFragment(this.id)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_new_product, container, false)
    }



    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == 10 && resultCode == RESULT_OK){
            val imageBitmap = data?.extras?.get("data") as Bitmap
            imageViewNewProduct.setImageBitmap(imageBitmap)
        }
        if(requestCode == 101 && resultCode == RESULT_OK){
            val imageUri = data?.data
            imageViewNewProduct.setImageURI(imageUri)
        }
    }

    fun startCamera() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if(intent.resolveActivity(requireContext().packageManager) != null){
            startActivityForResult(intent, 10)
        }
    }

    fun startGallery(){
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        if(intent.resolveActivity(requireContext().packageManager) != null){
            startActivityForResult(intent, 101)
        }
    }
}
