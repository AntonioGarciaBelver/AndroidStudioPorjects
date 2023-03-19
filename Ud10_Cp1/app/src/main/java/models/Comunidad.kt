package models

class Comunidad(var id: Int, var nombre: String, var capital: String, var bandera: Int, var latitud: Double, var longitud: Double, var icono: Int, var habitantes: String) {
    override fun toString(): String {
        return nombre
    }
}