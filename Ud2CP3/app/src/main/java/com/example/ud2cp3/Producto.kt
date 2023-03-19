package com.example.ud2cp3

open class Producto(open var fechaCaducidad:String, open var numeroLote: Int?){

    constructor():this("", null)

    override fun toString(): String {
        return "Producto (Fecha Caducidad = '$fechaCaducidad', Numero Lote = $numeroLote, "
    }


}