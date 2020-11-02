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
import com.example.stockmanagementsym.presentation.adapter.UserListAdapter
import kotlinx.android.synthetic.main.fragment_user.view.*

class UserListFragment : Fragment(), IListListener {

    private var userList: MutableList<User> = mutableListOf()
    private var adapter: UserListAdapter = UserListAdapter(userList)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_user, container, false)
        if (FragmentData.getUserPrivilege() != "admin") {
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

        view.recyclerViewUserList.adapter = adapter
        view.recyclerViewUserList.layoutManager = GridLayoutManager(view.context, 2)

        FragmentData.notifyUserLogic(this)

        view.buttonUserListToCreateUser.setOnClickListener(FragmentData.getController())
        view.buttonUserListToSearch.setOnClickListener(FragmentData.getController())
    }

    override fun reloadList(mutableList: MutableList<Any>) {
        adapter.setUserList(mutableList as MutableList<User>)
        requireActivity().runOnUiThread {
            adapter.notifyDataSetChanged()
        }
    }

    override fun addElementsToList(mutableList: MutableList<Any>) {
        adapter.getUserList().addAll(mutableList as MutableList<User>)
        requireActivity().runOnUiThread {
            adapter.notifyDataSetChanged()
        }
    }
}
