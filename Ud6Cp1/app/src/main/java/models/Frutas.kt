package models

class Frutas(var nombre: String, var imagen: Int=0, var origen: String) {
    override fun toString(): String {
        return nombre
    }
}