package com.example.stockmanagementsym.logic.classes

import com.example.stockmanagementsym.data.CONSTANTS
import com.example.stockmanagementsym.logic.business.User
import com.example.stockmanagementsym.logic.list_manager.IListManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jetbrains.anko.doAsyncResult
import org.jetbrains.anko.uiThread

class UserLogic : AbstractListLogic() {

    private var user: User? = null

    override fun loadData() {
        doAsyncResult {
            if (elementList.isEmpty())
                elementList = userLogicDao.selectUserList().toMutableList()
            uiThread {
                iListManager?.reloadList(elementList)
            }
        }
    }

    override fun insert(element: Any) {
        doAsyncResult {
            try {
                userLogicDao.insert(element as User)
                updateMutableList()
            } catch (e: Exception) {
                iListManager?.showResultTransaction(false)
            }
        }
    }

    override fun update(element: Any) {
        doAsyncResult {
            try {
                userLogicDao.update(element as User)
                updateMutableList()
            } catch (e: Exception) {
                iListManager?.showResultTransaction(false)
            }
        }
    }

    override fun updateMutableList() {
        doAsyncResult {
            try {
                elementList = userLogicDao.selectUserList().toMutableList()
                uiThread {
                    if (notifyUserTransaction)
                        notifyUserTransactionSuccess()
                }
            } catch (e: Exception) {
                iListManager?.showResultTransaction(false)
            }
        }
    }

    override fun delete(element: Any) {
        doAsyncResult {
            try {
                userLogicDao.delete(element as User)
                updateMutableList()
            } catch (e: Exception) {
                iListManager?.showResultTransaction(false)
            }
        }
    }

    override fun searchTextInMutableList(searchedText: String) {
        val listSearched = (elementList as MutableList<User>).filter { it.getName().toLowerCase().contains(searchedText.toLowerCase()) }
        iListManager?.reloadList(listSearched.toMutableList())
    }

    override fun setListManager(iListManager: IListManager) {
        this.iListManager = iListManager
    }

    override fun getUser(): User {
        if(user==null)
            user = User(CONSTANTS.STRING_VOID_ELEMENT,CONSTANTS.STRING_VOID_ELEMENT,CONSTANTS.STRING_VOID_ELEMENT,CONSTANTS.STRING_VOID_ELEMENT,CONSTANTS.DEFAULT_USER_LATITUDE,CONSTANTS.DEFAULT_USER_LONGITUDE)
        return user!!
    }

    override fun confirmLogin(userName: String, password: String) {
        user = userLogicDao.selectUser(userName, password)
    }
}
