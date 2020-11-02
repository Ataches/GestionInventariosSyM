package com.example.stockmanagementsym.presentation.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.example.stockmanagementsym.presentation.fragment.CartListFragment
import com.example.stockmanagementsym.presentation.fragment.ProductListFragment

class ShopPagerAdapter(fm: FragmentManager): FragmentStatePagerAdapter(fm) {
    override fun getItem(position: Int): Fragment {
        return when(position) {
            1 -> CartListFragment() // It's necessary that cart list loads before product list to send cart listener to product list
            0 -> ProductListFragment()
            else -> ProductListFragment()
        }
    }

    override fun getCount(): Int {
        return 2
    }

    override fun getPageTitle(position: Int): CharSequence {
        return when(position) {
            1 -> "Carrito"
            0 -> "Productos"
            else -> "Productos"
        }
    }
}