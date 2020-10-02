package com.example.gestioninventariossym.logica.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.gestioninventariossym.logica.fragments.CartFragment
import com.example.gestioninventariossym.logica.fragments.ProductsListFragment

class TabAdapter(fm: FragmentManager): FragmentPagerAdapter(fm) {
    override fun getPageTitle(position: Int): CharSequence? {
        if(position == 0)
            return "Productos"
        return "Carrito"
    }

    override fun getCount(): Int {
        return 2
    }

    override fun getItem(position: Int): Fragment {
        if(position == 0)
            return ProductsListFragment()
        return CartFragment()
    }

}