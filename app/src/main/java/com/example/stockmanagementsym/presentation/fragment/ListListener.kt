package com.example.stockmanagementsym.presentation.fragment

interface ListListener {
    //Method used to reload the data of recycler view adapters
    fun reloadList()
    //Method used to put the search list in recycler view adapters
    fun setList(list: MutableList<Any>)
}