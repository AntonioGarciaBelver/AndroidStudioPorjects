package com.jlara.ejemplosqlite_kotlin_u

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context):
    SQLiteOpenHelper(context, ComunidadContract.Companion.Entrada.NOMBRE_TABLA, null,
                                                            ComunidadContract.Companion.VERSION) {
    companion object{
        const val CREATE_COMUNIDADES_TABLA="CREATE TABLE "+ ComunidadContract.Companion.Entrada.NOMBRE_TABLA+
                " ("+ComunidadContract.Companion.Entrada.COLUMNA_ID+" INT "+
                ComunidadContract.Companion.Entrada.COLUMNA_NOMBRE+" TEXT,"+
                ComunidadContract.Companion.Entrada.COLUMNA_BANDERA+" INT)"


        const val REMOVE_PAISES_TABLA="DROP TABLE IF EXISTS "+ComunidadContract.Companion.Entrada.NOMBRE_TABLA
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(CREATE_COMUNIDADES_TABLA)
    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        db?.execSQL(REMOVE_PAISES_TABLA)
        onCreate(db)
    }
}

