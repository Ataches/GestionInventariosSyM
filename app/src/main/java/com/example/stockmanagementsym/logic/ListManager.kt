package com.example.stockmanagementsym.logic

import com.example.stockmanagementsym.presentation.fragment.ListListener
import com.example.stockmanagementsym.presentation.view.Notifier

/*
    Created by Juan Sebastian Sanchez Mancilla on 30/10/2020
*/
class ListManager(private val notifier: Notifier, private val listener: ListListener) {

    fun showToastMessage(message: Int){
        notifier.showToastMessage(message)
    }
    fun showAlertMessage(tittle: Int, message:Int){
        notifier.showAlertMessage(tittle,message)
    }

    fun reloadList() {
        listener.reloadList()
    }
}