package com.example.stockmanagementsym.presentation.view_holder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.stockmanagementsym.R
import com.example.stockmanagementsym.logic.business.User
import com.example.stockmanagementsym.presentation.fragment.FragmentData
import kotlinx.android.synthetic.main.item_user.view.*

class UserListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bind(user: User) {
        itemView.textViewName.text = user.getName()
        if(FragmentData.getUserPrivilege()!="admin"){
            itemView.buttonDeleteUser.visibility = View.GONE
            itemView.buttonDeleteUser.isEnabled = false
            itemView.buttonDeleteUser.isClickable = false
        }
        FragmentData.paintPhoto(
            user.getPhotoData(),
            itemView.imageViewUserItem,
            R.drawable.ic_person
        )

        itemView.textViewPrivilege.text = user.getPrivilege()
        itemView.buttonDeleteUser.setOnClickListener {
            FragmentData.deleteUser(user)
        }
    }
}