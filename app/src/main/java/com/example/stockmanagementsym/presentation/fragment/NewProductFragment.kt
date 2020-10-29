package com.example.stockmanagementsym.presentation.fragment

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.stockmanagementsym.R
import com.example.stockmanagementsym.presentation.AndroidController
import com.example.stockmanagementsym.presentation.view.FragmentData
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_home.view.*
import kotlinx.android.synthetic.main.fragment_new_product.view.*

class NewProductFragment: Fragment(){

    private lateinit var viewElement:View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_new_product, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewElement = view
        (activity as AppCompatActivity?)!!.supportActionBar!!.title = getString(R.string.product)

        if(FragmentData.getBooleanUpdate()){
            val product = FragmentData.getProductToEdit()
            view.textViewTitleProduct.text = getString(R.string.titleAlertUpdateProd)
            view.editTextProductName.setText(product.getName())
            view.editTextProductPrice.setText(product.getPrice().toString())
            view.editTextProductDesc.setText(product.getDescription())
            view.editTextProductQuantity.setText(product.getQuantity().toString())

            val userPhotoData = product.getStringBitMap()

            if(userPhotoData!=""){
                if(userPhotoData.length<400){
                    try {
                        Picasso.get().load(userPhotoData).into(view.imageViewNewProduct)
                        view.imageViewNewProduct.background = null
                    }catch (e:Exception){
                        FragmentData.showToastMessage(view.context, ""+e)
                    }
                }else{
                    view.imageViewNewProduct.setImageBitmap(FragmentData.getBitMapFromString(userPhotoData))
                    view.imageViewNewProduct.visibility = View.VISIBLE
                    view.imageViewNewProduct.background = null
                }
            }
        }else{
            view.textViewTitleProduct.text = getString(R.string.titleAlertNewProd)
        }

        view.buttonNewProductToHome.setOnClickListener (AndroidController)
        view.buttonNewProductToProductList.setOnClickListener (AndroidController)
        view.buttonNewProduct.setOnClickListener (AndroidController)
        view.buttonNewProductToGallery.setOnClickListener(AndroidController)
        view.buttonNewProductToCamera.setOnClickListener(AndroidController)
    }

}
