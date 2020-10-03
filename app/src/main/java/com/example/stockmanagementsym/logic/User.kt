<<<<<<< HEAD
package com.example.stockmanagementsym.logic
=======
package com.example.stocmanagementsym.logic
>>>>>>> temp

object User {
    private lateinit var name: String
    fun setUser(name:String){
        this.name = name
    }
    fun getUser(): String {
        return name
    }
}