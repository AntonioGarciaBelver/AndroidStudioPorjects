package models

class Comunidad(var id: Int, var nombre : String, var imagen : Int) {

    override fun toString(): String {
        return "Comunidad(ID='$id' || pais='$nombre')"
    }
}