package com.gorillazuniversity.clase8_1

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.example.stockmanagementsym.presentation.fragment.CartListFragment
import com.example.stockmanagementsym.presentation.fragment.ProductListFragment

class ShopPagerAdapter(fm: FragmentManager): FragmentStatePagerAdapter(fm) {
    override fun getItem(position: Int): Fragment {
        return when(position) {
            0 -> ProductListFragment()
            1 -> CartListFragment()
            else -> ProductListFragment()
        }
    }

    override fun getCount(): Int {
        return 2
    }

    override fun getPageTitle(position: Int): CharSequence {
        return when(position) {
            0 -> "Productos"
            1 -> "Carrito"
            else -> "Productos"
        }
    }
}