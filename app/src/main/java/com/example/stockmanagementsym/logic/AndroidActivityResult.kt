package com.example.stockmanagementsym.logic

import android.app.Activity
import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.ImageView
import com.example.stockmanagementsym.ILauncher
import com.example.stockmanagementsym.data.CONSTANTS
import com.example.stockmanagementsym.presentation.fragment.FragmentData
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions


/**
 * Created by Juan Sebastian Sanchez Mancilla on 4/11/2020
 */
class AndroidActivityResult(private val activity:Activity, private val context: Context, private val iLauncher: ILauncher) {

    private var registerBoolean: Boolean = false
    private lateinit var googleSignInClient: GoogleSignInClient
    private var account: GoogleSignInAccount? = null

    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val newUserImageView: ImageView? = FragmentData.getNewUserImageView()
        val newProductImageView: ImageView? = FragmentData.getNewProductFragmentView()

        if (requestCode == CONSTANTS.CAMERA_INTENT_CODE && resultCode == RESULT_OK) {
            if (newProductImageView != null)
                camera(newProductImageView, data)
            if (newUserImageView != null)
                camera(newUserImageView, data)
        }
        if (requestCode == CONSTANTS.GALLERY_INTENT_CODE && resultCode == RESULT_OK) {
            if (newProductImageView != null)
                gallery(newProductImageView, data)
            if (newUserImageView != null)
                gallery(newUserImageView, data)
        }
        if ((requestCode == CONSTANTS.GOOGLE_USER_LOGIN_INTENT_CODE) || (requestCode == CONSTANTS.GOOGLE_USER_REGISTER_INTENT_CODE)) {
            checkUserCredentials(requestCode, data)
        }
    }

    private fun camera(imageView: ImageView?, data: Intent?) {
        val bitMap = data?.extras?.get("data") as Bitmap

        imageView?.setImageBitmap(bitMap)
        imageView?.visibility = View.VISIBLE

        FragmentData.setBitMap(bitMap)
    }

    private fun gallery(imageView: ImageView?, data: Intent?) {
        val imageUri = data?.data

        imageView?.setImageURI(imageUri)
        imageView?.visibility = View.VISIBLE

        val bitMap = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) { // Check android version to decode the image
            ImageDecoder.decodeBitmap(ImageDecoder.createSource(context.contentResolver, imageUri!!))
        } else {
            MediaStore.Images.Media.getBitmap(context.contentResolver, imageUri)
        }
        FragmentData.setBitMap(bitMap)
    }

    private fun checkUserCredentials(requestCode: Int, data: Intent?) {
        val androidModel = iLauncher.getAndroidModel()

            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
        try {
            account = task.result
            androidModel.setGoogleAccount(account!!)
            androidModel.setGoogleSingInClient(googleSignInClient)
            if (requestCode == CONSTANTS.GOOGLE_USER_LOGIN_INTENT_CODE) {
                androidModel.confirmLogin(account!!.displayName.toString(), account!!.id.toString())
            }
            if (requestCode == CONSTANTS.GOOGLE_USER_REGISTER_INTENT_CODE)
                androidModel.register(account!!.email.toString(),account!!.displayName.toString(), account!!.id.toString())
        } catch (e: Exception) {
            androidModel.showMessageLoginFail()
        }
    }

    fun setRegisterBoolean(registerBoolean: Boolean) {
        this.registerBoolean = registerBoolean
    }

    fun startGoogleIntent() {
        configureGoogleClient()
        account = GoogleSignIn.getLastSignedInAccount(context)
        val intent = googleSignInClient.signInIntent
        if (registerBoolean) {
            registerBoolean = false
            activity.startActivityForResult(intent,CONSTANTS.GOOGLE_USER_REGISTER_INTENT_CODE)
        } else
            activity.startActivityForResult(intent, CONSTANTS.GOOGLE_USER_LOGIN_INTENT_CODE)
    }

    private fun configureGoogleClient() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestProfile()
                .requestEmail()
                .requestId()
                .build()

        googleSignInClient = GoogleSignIn.getClient(activity, gso)
    }
}