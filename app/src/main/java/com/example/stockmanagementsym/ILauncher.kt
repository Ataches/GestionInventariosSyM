package com.example.stockmanagementsym

import com.example.stockmanagementsym.presentation.AndroidModel

interface ILauncher {
    fun getAndroidModel(): AndroidModel
    fun goBackFromNewUser() {}
}
