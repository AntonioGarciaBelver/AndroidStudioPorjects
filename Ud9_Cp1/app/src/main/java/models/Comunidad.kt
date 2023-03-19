package models

class Comunidad(var id: Int, var nombre: String, var bandera: Int) {
    override fun toString(): String {
        return nombre
    }
}