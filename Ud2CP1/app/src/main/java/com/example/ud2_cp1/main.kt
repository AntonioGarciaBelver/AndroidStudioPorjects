package com.example.ud2_cp1

import java.time.Year

fun main(){

    var X = serVivo(3)
    var Y = serVivo(5)

    println(X.equals(Y))

    println(Y.mayor(X))
    Y.mayor(X)

    println("------------------------------")

    X = humano("Homero",34)
    Y = humano("Bart",9)

    println(X.equals(Y))

    println(Y.mayor(X))
    Y.mayor(X)

}