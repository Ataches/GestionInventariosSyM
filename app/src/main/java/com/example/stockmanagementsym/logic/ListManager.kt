package com.example.stockmanagementsym.logic

import com.example.stockmanagementsym.presentation.fragment.ListListener
import com.example.stockmanagementsym.presentation.view.NotifierView

/*
    Created by Juan Sebastian Sanchez Mancilla on 30/10/2020
*/
class ListManager(private val listener: ListListener?,private val notifierView: NotifierView) {

    fun showToastMessage(message: Int){
        notifierView.showToastMessage(message)
    }
    fun showAlertMessage(tittle: Int, message:Int){
        notifierView.showAlertMessage(tittle,message)
    }
    fun showResultTransaction(resultTransaction:Boolean){
        notifierView.showResultTransaction(resultTransaction)
    }

    fun reloadList() {
        listener?.reloadList()
    }
}