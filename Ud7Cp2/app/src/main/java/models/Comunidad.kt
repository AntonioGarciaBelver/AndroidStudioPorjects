package models

class Comunidad(var nombre: String, var imagen: Int=0) {
    override fun toString(): String {
        return nombre
    }
}