package com.example.ud2_cp1

open class serVivo(var edad:Byte){

    fun equals(otro:serVivo):Boolean {
        return this.edad==otro.edad
    }

    fun mayor(otro:serVivo):serVivo{
        if(this.edad>=otro.edad){
            return this
        }else{
            return otro
        }
    }

    override fun toString():String{

        return "El ser vivo tiene $edad años"
    }

}