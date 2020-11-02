package com.example.stockmanagementsym.logic.list_manager

import com.example.stockmanagementsym.presentation.fragment.IListListener
import com.example.stockmanagementsym.presentation.view.NotifierView

/*
    Created by Juan Sebastian Sanchez Mancilla on 30/10/2020
*/
class ListManager : IListManager {

    private var listener: IListListener? = null
    private var notifierView: NotifierView? = null

    override fun showToastMessage(message: Int) {
        notifierView?.showToastMessage(message)
    }

    override fun showAlertMessage(tittle: Int, message: Int) {
        notifierView?.showAlertMessage(tittle, message)
    }

    override fun showResultTransaction(resultTransaction: Boolean) {
        notifierView?.showResultTransaction(resultTransaction)
    }

    override fun reloadList(mutableList: MutableList<Any>) {
        listener?.reloadList(mutableList)
    }

    override fun addElementsToList(mutableList: MutableList<Any>) {
        listener?.addElementsToList(mutableList)
    }

    override fun setListListener(listener: IListListener) {
        this.listener = listener
    }

    override fun setNotifier(notifierView: NotifierView) {
        this.notifierView = notifierView
    }
}