package com.example.stockmanagementsym.presentation.fragment

interface ListListener {
    fun reloadList()
    fun setList(list: MutableList<Any>)
}