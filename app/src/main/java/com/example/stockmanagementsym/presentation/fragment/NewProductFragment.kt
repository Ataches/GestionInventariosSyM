package com.example.stockmanagementsym.presentation.fragment

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.stockmanagementsym.R
import com.example.stockmanagementsym.logic.business.Product
import com.example.stockmanagementsym.presentation.AndroidController
import com.example.stockmanagementsym.presentation.view.FragmentData
import kotlinx.android.synthetic.main.fragment_new_product.*
import java.io.ByteArrayOutputStream

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
        (activity as AppCompatActivity?)!!.supportActionBar!!.title = getString(R.string.product)

        if(FragmentData.getBooleanUpdate()){
            val product = FragmentData.getProductToEdit()
            editTextProductName.setText(product.getName())
            editTextProductPrice.setText(product.getPrice().toString())
            editTextProductDesc.setText(product.getDescription())
            editTextProductQuantity.setText(product.getQuantity().toString())
            imageViewNewProduct.setImageBitmap(decoderStringToBitMap(product.getStringBitMap()))
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
            val bitMap = data?.extras?.get("data") as Bitmap

            imageViewNewProduct.setImageBitmap(bitMap)

            FragmentData.setStringBitMap(encoderBitMapToString(bitMap))
        }
        if(requestCode == 101 && resultCode == RESULT_OK){
            val imageUri = data!!.data!!

            imageViewNewProduct.setImageURI(imageUri)

            val bitMap = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                ImageDecoder.decodeBitmap(ImageDecoder.createSource(requireContext().contentResolver, imageUri))
            } else {
                MediaStore.Images.Media.getBitmap(requireContext().contentResolver, imageUri)
            }
            FragmentData.setStringBitMap(encoderBitMapToString(bitMap))
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
    private fun encoderBitMapToString(imageBitmap: Bitmap): String{
        val byteArrayOutputStream =  ByteArrayOutputStream()
        imageBitmap.compress(Bitmap.CompressFormat.PNG,100, byteArrayOutputStream)
        val byteArray = byteArrayOutputStream.toByteArray()
        return Base64.encodeToString(byteArray, Base64.DEFAULT)
    }

    private fun decoderStringToBitMap(string: String): Bitmap? {
        val byteArrayFromString = Base64.decode(string, Base64.DEFAULT)
        return BitmapFactory.decodeByteArray(byteArrayFromString,0,byteArrayFromString.size)
    }
}
