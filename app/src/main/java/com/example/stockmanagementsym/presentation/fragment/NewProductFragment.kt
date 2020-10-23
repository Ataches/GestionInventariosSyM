package com.example.stockmanagementsym.presentation.fragment

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.provider.MediaStore
import android.util.Base64
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.stockmanagementsym.R
import com.example.stockmanagementsym.presentation.AndroidController
import com.example.stockmanagementsym.presentation.view.FragmentData
import kotlinx.android.synthetic.main.fragment_new_product.*
import java.io.ByteArrayOutputStream
import java.io.File

class NewProductFragment: Fragment(){

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_new_product, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if(FragmentData.getBooleanUpdate()){
            editTextProductName.setText(FragmentData.getProductToEdit().getName())
            editTextProductPrice.setText(FragmentData.getProductToEdit().getPrice().toString())
            editTextProductDesc.setText(FragmentData.getProductToEdit().getDescription())
            editTextProductQuantity.setText(FragmentData.getProductToEdit().getQuantity().toString())
        }

        buttonNewProductToHome.setOnClickListener (AndroidController)
        buttonNewProductToProductList.setOnClickListener (AndroidController)
        buttonNewProductToNewProduct.setOnClickListener (AndroidController)
        buttonNewProductToGallery.setOnClickListener(AndroidController)
        buttonNewProductToCamera.setOnClickListener(AndroidController)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == 10 && resultCode == RESULT_OK){
            val imageBitmap = data?.extras?.get("data") as Bitmap

            val byteArrayOutputStream =  ByteArrayOutputStream()
            imageBitmap.compress(Bitmap.CompressFormat.PNG,100, byteArrayOutputStream)
            val byteArray = byteArrayOutputStream.toByteArray()
            val stringBitmap = Base64.encodeToString(byteArray, Base64.DEFAULT)

            val byteArrayFromString = Base64.decode(stringBitmap,Base64.DEFAULT)
            val bitMap=BitmapFactory.decodeByteArray(byteArrayFromString,0,byteArrayFromString.size)

            imageViewNewProduct.setImageBitmap(bitMap)

            FragmentData.setStringBitMap(stringBitmap)
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
    fun decoder(base64Str: String, pathFile: String): Unit{
        val imageByteArray = Base64.decode(base64Str,0)
        File(pathFile).writeBytes(imageByteArray)
    }
    fun encoder(filePath: String): String{
        val bytes = File(filePath).readBytes()
        return Base64.encodeToString(bytes,0)
    }
}
