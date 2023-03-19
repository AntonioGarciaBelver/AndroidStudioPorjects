package com.example

import android.provider.BaseColumns

class ComunidadesContract {
    companion object {
        const val VERSION = 1
        class Entrada : BaseColumns {
            companion object {
                const val NOMBRE_TABLA = "comunidades"
                const val COLUMNA_ID = "id"
                const val COLUMNA_NOMBRE = "nombre"
                const val COLUMNA_CAPITAL = "capital"
                const val COLUMNA_BANDERA = "bandera"
                const val COLUMNA_LATITUD = "latitud"
                const val COLUMNA_LONGITUD = "longitud"
                const val COLUMNA_ICONO = "icono"
                const val COLUMNA_HABITANTES = "habitantes"
            }
        }
    }
}

