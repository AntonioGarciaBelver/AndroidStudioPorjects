package com.example.ud2cp3

class ProductoCongelado(override var fechaCaducidad:String, override var numeroLote: Int?,
                        var temperaturaCongelado:Double): Producto(fechaCaducidad, numeroLote){

    override fun toString(): String {
        return super.toString() + "Temperatura Congelado = $temperaturaCongelado, Tipo = Congelado)"
    }
}