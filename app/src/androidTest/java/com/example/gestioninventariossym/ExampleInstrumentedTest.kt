package com.example.gestioninventariossym.logica.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class TabAdapter(fm: FragmentManager): FragmentPagerAdapter(fm) {
    override fun getPageTitle(position: Int): CharSequence? {
        if(position == 0)
            return "Productos"
        return "Carrito"
    }

    override fun getItem(position: Int): Fragment {
        return Fragment()
    }

    override fun getCount(): Int {
        return 2
    }

}