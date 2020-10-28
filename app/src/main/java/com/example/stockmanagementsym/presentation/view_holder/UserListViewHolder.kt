package com.example.stockmanagementsym.presentation.view_holder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.stockmanagementsym.logic.business.User
import com.example.stockmanagementsym.presentation.view.FragmentData
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_user.view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class UserListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bind(user: User) {
        itemView.textViewName.text = user.getName()
        if(FragmentData.getUserPrivilege()!="admin"){
            itemView.buttonDeleteUser.visibility = View.GONE
            itemView.buttonDeleteUser.isEnabled = false
            itemView.buttonDeleteUser.isClickable = false
        }
        val userPhotoData = user.getPhotoData()
        if(userPhotoData!=""){
            if(userPhotoData.length<400){
                try {
                    Picasso.get().load(userPhotoData).into(itemView.imageViewUserItem)
                    itemView.imageViewUserItem.background = null
                }catch (e:Exception){
                    FragmentData.showToastMessage(itemView.context, ""+e)
                }
            }else{
                itemView.imageViewUserItem.setImageBitmap(FragmentData.getBitMapFromString(userPhotoData))
                itemView.imageViewUserItem.background = null
            }
        }
        itemView.textViewPrivilege.text = user.getPrivilege()
        itemView.buttonDeleteUser.setOnClickListener {
            GlobalScope.launch (Dispatchers.IO){
                FragmentData.deleteUser(user)
            }
        }
    }
}