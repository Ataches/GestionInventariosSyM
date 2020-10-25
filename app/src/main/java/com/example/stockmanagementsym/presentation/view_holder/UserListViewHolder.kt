package com.example.stockmanagementsym.presentation.view_holder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.stockmanagementsym.logic.business.User
import com.example.stockmanagementsym.presentation.view.FragmentData
import kotlinx.android.synthetic.main.item_user.view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class UserListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bind(user: User) {
        itemView.textViewName.text = user.getName()
        if(FragmentData.getUserPrivileges()!="admin"){
            itemView.buttonDeleteUser.visibility = View.GONE
            itemView.buttonDeleteUser.isEnabled = false
            itemView.buttonDeleteUser.isClickable = false
        }
        itemView.buttonDeleteUser.setOnClickListener {
            GlobalScope.launch (Dispatchers.IO){
                FragmentData.deleteUser(user)
            }
        }
    }
}