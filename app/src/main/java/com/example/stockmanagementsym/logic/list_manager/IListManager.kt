package com.example.stockmanagementsym.logic.list_manager

import com.example.stockmanagementsym.presentation.fragment.IListListener
import com.example.stockmanagementsym.presentation.view.NotifierView

interface IListManager {
    fun showToastMessage(message: Int)
    fun showAlertMessage(tittle: Int, message: Int)
    fun showResultTransaction(resultTransaction: Boolean)

    fun reloadList(mutableList: MutableList<Any>)
    fun addElementsToList(mutableList: MutableList<Any>)

    fun setListListener(listener: IListListener?)
    fun setNotifier(notifierView: NotifierView?)
}