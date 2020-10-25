package com.example.stockmanagementsym.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.stockmanagementsym.R
import com.example.stockmanagementsym.logic.business.User
import com.example.stockmanagementsym.presentation.AndroidController
import com.example.stockmanagementsym.presentation.adapter.UserListAdapter
import com.example.stockmanagementsym.presentation.view.FragmentData
import kotlinx.android.synthetic.main.fragment_user.*
import kotlinx.android.synthetic.main.fragment_user.view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class UserListFragment : Fragment(), ListListener {

    private var userList: List<User> = listOf()
    private var adapter: UserListAdapter = UserListAdapter(userList)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_user, container, false)
        if(FragmentData.getUserPrivilege()!="admin"){
            view.buttonUserListToCreateUser.visibility = View.GONE
            view.buttonUserListToCreateUser.isEnabled = false
            view.buttonUserListToCreateUser.isClickable = false
        }
        // Inflate the layout for this fragment
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity?)!!.supportActionBar!!.title = getString(R.string.userList)

        FragmentData.setUserListListener(this)
        reloadList()

        recyclerViewUserList.adapter = adapter
        recyclerViewUserList.layoutManager = GridLayoutManager(view.context, 2)

        buttonUserListToCreateUser.setOnClickListener(AndroidController)
    }

    override fun reloadList(){
        GlobalScope.launch(Dispatchers.IO){
            adapter.setUserList(FragmentData.getUserList())
            requireActivity().runOnUiThread {
                adapter.notifyDataSetChanged()
            }
        }
    }

    override fun setList(list: MutableList<Any>) {
        GlobalScope.launch(Dispatchers.IO){
            adapter.setUserList(list as MutableList<User>)
            requireActivity().runOnUiThread {
                adapter.notifyDataSetChanged()
            }
        }
    }
}
