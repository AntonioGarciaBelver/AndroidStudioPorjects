package com.example

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import models.Comunidad

class ComunidadCRUD(context: Context) {
    private var helper: DatabaseHelper?=null

    init{
        helper= DatabaseHelper(context)
    }

    fun newComunidad(item:Comunidad){
        //Abrimos la BD en modo escritura
        val db:SQLiteDatabase=helper?.writableDatabase!!
        //mapeo de columnas con valores a insertar en la BD
        val values=ContentValues()
        values.put(ComunidadesContract.Companion.Entrada.COLUMNA_ID,item.id)
        values.put(ComunidadesContract.Companion.Entrada.COLUMNA_NOMBRE,item.nombre)
        values.put(ComunidadesContract.Companion.Entrada.COLUMNA_BANDERA,item.bandera)

        //insertar una fila
        //db.insert(AlumnosContract.Companion.Entrada.NOMBRE_TABLA,null,values)
        val consulta = "INSERT INTO comunidades VALUES('" + item.id + "','"+item.nombre+"'," +
                "'" + item.bandera+"');"
        db.execSQL(consulta)
        db.close()
    }


    fun getComunidades(): MutableList<Comunidad>{
        val items:MutableList<Comunidad> =mutableListOf()
        //Abrimos la BD en modo lectura
        val db:SQLiteDatabase=helper?.readableDatabase!!
        //especificamos las columnas a leer
        val columnas =arrayOf(
            ComunidadesContract.Companion.Entrada.COLUMNA_ID,
            ComunidadesContract.Companion.Entrada.COLUMNA_NOMBRE,
            ComunidadesContract.Companion.Entrada.COLUMNA_BANDERA)
        //creamos un cursor
        val c: Cursor =db.query(
            ComunidadesContract.Companion.Entrada.NOMBRE_TABLA,
                        columnas,null,null,null,null,null)
        //recorremos el cursor
        while (c.moveToNext()) {
            items.add(
                Comunidad(
                    c.getInt(c.getColumnIndexOrThrow(ComunidadesContract.Companion.Entrada.COLUMNA_ID)),
                    c.getString(c.getColumnIndexOrThrow(ComunidadesContract.Companion.Entrada.COLUMNA_NOMBRE)),
                    c.getInt(c.getColumnIndexOrThrow(ComunidadesContract.Companion.Entrada.COLUMNA_BANDERA))

                )
            )
        }

        c.close()
        db.close()
        return items
    }

    fun updateComunidad(item:Comunidad){
        val db:SQLiteDatabase=helper?.writableDatabase!!
        //mapeo de columnas con valores a insertar en la BD
        val values=ContentValues()
        values.put(ComunidadesContract.Companion.Entrada.COLUMNA_ID,item.id)
        values.put(ComunidadesContract.Companion.Entrada.COLUMNA_NOMBRE,item.nombre)
        //actualizamos la tabla
        db.update(ComunidadesContract.Companion.Entrada.NOMBRE_TABLA,values," id=?",arrayOf(item.id.toString()))
        db.close()
    }

    fun deleteComunidad(item:Comunidad){
        val db:SQLiteDatabase=helper?.writableDatabase!!
        db.delete(ComunidadesContract.Companion.Entrada.NOMBRE_TABLA," id=?", arrayOf(item.id.toString()))
        db.close()
    }
}