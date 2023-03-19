package com.example.ud2_cp2

class Persona(var nombre:String, var apellido:String){

    constructor(): this("","")

    var telefono:String = ""
    set(value) {
        if(value.length == 9){
            field = value
        }else{
            println("Ingresa 9 digitos porfavor")
        }
    }
    get() = field

    override fun toString(): String {
        if(telefono.length!=9){
            return "Nombre: $nombre" +
                    "\nApellido: $apellido" +
                    "\nTelefono: "
        }else{
            return "Nombre: $nombre" +
                    "\nApellido: $apellido" +
                    "\nTelefono: $telefono"
        }
    }

}
