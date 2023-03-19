package com.example

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context):
    SQLiteOpenHelper(context, ComunidadesContract.Companion.Entrada.NOMBRE_TABLA, null,
        ComunidadesContract.Companion.VERSION) {
    companion object{
        const val CREATE_COMUNIDADES_TABLA="CREATE TABLE "+ ComunidadesContract.Companion.Entrada.NOMBRE_TABLA+
                " ("+ ComunidadesContract.Companion.Entrada.COLUMNA_ID+" TEXT PRIMARY KEY, "+
                ComunidadesContract.Companion.Entrada.COLUMNA_NOMBRE+" TEXT,"+
                ComunidadesContract.Companion.Entrada.COLUMNA_BANDERA+" INT)"



        const val REMOVE_PAISES_TABLA="DROP TABLE IF EXISTS "+ ComunidadesContract.Companion.Entrada.NOMBRE_TABLA
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(CREATE_COMUNIDADES_TABLA)
    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        db?.execSQL(REMOVE_PAISES_TABLA)
        onCreate(db)
    }
}

