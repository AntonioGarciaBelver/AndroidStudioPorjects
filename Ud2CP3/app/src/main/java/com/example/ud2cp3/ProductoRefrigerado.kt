package com.example.ud2cp3

class ProductoRefrigerado(override var fechaCaducidad:String, override var numeroLote: Int?,
                          var codigoSupervision:String): Producto(fechaCaducidad, numeroLote){

    override fun toString(): String {
        return super.toString()+ "Codigo Supervision='$codigoSupervision', Tipo = Refrigerado)"
    }
}