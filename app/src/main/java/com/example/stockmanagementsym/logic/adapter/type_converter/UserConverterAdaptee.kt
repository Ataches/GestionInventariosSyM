package com.example.stockmanagementsym.logic.adapter.type_converter

import com.example.stockmanagementsym.data.CONSTANTS
import com.example.stockmanagementsym.logic.business.User

/*
    Created by Juan Sebastian Sanchez Mancilla on 30/10/2020
*/
class UserConverterAdaptee {

    fun getUserToString(user: User): String {
        return  "Nombre: "+user.getName()+ CONSTANTS.STRING_NEW_LINE+
                "Direccion: "+user.getPrivilege()
    }
}