package com.example.stockmanagementsym.presentation.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.example.stockmanagementsym.R
import com.gorillazuniversity.clase8_1.ShopPagerAdapter
import kotlinx.android.synthetic.main.fragment_shop.*

class ShopFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_shop, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity?)!!.supportActionBar!!.title = getString(R.string.shopFragment)

        tabsShop.setupWithViewPager(viewShop)
        viewShop.adapter = ShopPagerAdapter(parentFragmentManager)
    }

    override fun onResume() {
        super.onResume()
        viewShop.adapter?.notifyDataSetChanged()
    }

}