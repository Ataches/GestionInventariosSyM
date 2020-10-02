package com.example.gestioninventariossym.datos

data class Cliente(private val nombre: String, private val direccion: String, private val telefono: String){
    private lateinit var listadoClientes : List<Cliente>
    fun getNombre():String{
        return nombre
    }
    fun getTelefono():String{
        return telefono
    }
    fun getDireccion():String{
        return direccion
    }
    fun createClientes():List<Cliente>{
         listadoClientes = listOf(
            Cliente("Carlos Gutierrez", "Cr 9 #20 68", "3203232321"),
            Cliente("Antonio Perez", "Cra 9 #30 60", "3001231231"),
            Cliente("Miguel Velasquez", "Calle 6a #20 22", "3103828329")
        )
        return listadoClientes
    }
}