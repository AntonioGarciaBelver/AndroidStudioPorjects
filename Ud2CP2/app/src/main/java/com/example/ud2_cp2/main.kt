package com.example.ud2_cp2

fun main(){

    var p1 = Persona("Antonio", "Garcia")
    var p2 = Persona("Loren", "Lynch")

    p1.telefono = "123456789"
    p1.telefono = "12345678"

    var c1 = Cuenta("99999999", p1)
    var c2 = Cuenta("11111111", p2)

    c1.saldo = 1000.0
    c2.saldo = 1000.0

    print("Saldo inicial -> ")
    println(c1.saldo)
    print("Saldo inicial -> ")
    println(c2.saldo)

    c1.transaccion(500.0, "ingreso")
    c2.transaccion(700.0, "reintegro")
    c1.transaccion(40.0, "ingreso")
    c2.transaccion(350.0, "reintegro")
    println("----------------------------")

    println(c1)
    println(c2)

}