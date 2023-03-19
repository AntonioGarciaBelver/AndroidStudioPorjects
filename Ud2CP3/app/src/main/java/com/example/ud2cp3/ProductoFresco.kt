package com.example.ud2cp3

class ProductoFresco(var fechaEnvasado:String, override var fechaCaducidad: String,
                     override var numeroLote: Int?, var paisOrigen:String): Producto(fechaCaducidad, numeroLote){

    override fun toString(): String {
        return super.toString() + "Fecha Envasado= '$fechaEnvasado', Pais Origen= '$paisOrigen', Tipo = Fresco)"
    }

}