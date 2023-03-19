package com.example.ud2_cp2

class Cuenta(var numeroCuenta:String, var propietario: Persona?){

    constructor():this("",null )

    var saldo: Double = 0.0
        set(value) {
            if (value >= 0){
                field = value
            }
        }get() = field

    fun transaccion(cantidad:Double, tipoTransaccion:String){
        if(tipoTransaccion == "reintegro"){
            if(cantidad >saldo){
                saldo = 0.0
                println("Reintegro de $cantidad. Saldo actual de $saldo")
            }else{
                saldo -= cantidad
                println("Reintegro de $cantidad. Saldo actual de $saldo")
            }
        }else if(tipoTransaccion == "ingreso"){
            saldo += cantidad
            println("Ingreso de $cantidad. Saldo actual de $saldo")
        }
    }

    override fun toString(): String {
        return "$propietario " +
                "\nNumero Cuenta: $numeroCuenta" +
                "\nSaldo: $saldo" +
                "\n-------------------------------"
    }


}