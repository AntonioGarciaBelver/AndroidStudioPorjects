package com.example.ud2_cp1

class humano(var nombre:String, edad: Byte): serVivo(edad){

    fun equals(otro:humano):Boolean {
        return this.nombre == otro.nombre && this.edad == otro.edad
    }

    fun mayor(otro:humano):serVivo{
        if(this.edad == otro.edad){
            if(this.nombre.length>otro.nombre.length){
                return this
            }else{
                return otro
            }
        }
        if(this.edad>otro.edad){
            return this
        }else{
            return otro
        }
    }

    override fun toString():String{

        return "El humano se llama $nombre y tiene $edad años"
    }
}