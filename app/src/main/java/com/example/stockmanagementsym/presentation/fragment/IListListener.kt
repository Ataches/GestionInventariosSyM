package com.example.stockmanagementsym.presentation.fragment

interface IListListener {
    //Method used to reload the data of recycler view adapters
    fun reloadList(mutableList: MutableList<Any>)
    //Method used to add elements to list in recycler view adapters
    fun addElementsToList(mutableList:MutableList<Any>)
}