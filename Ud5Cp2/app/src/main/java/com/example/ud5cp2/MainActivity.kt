package com.example.ud5cp2

import android.app.DatePickerDialog
import android.app.Dialog
import android.content.ClipData.Item
import android.os.Build.VERSION_CODES.R
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextMenu
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView.AdapterContextMenuInfo
import android.widget.Button
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var dialogo: Dialog
    var banderas: MutableList<Comunidad> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val lvDatos: ListView = findViewById(R.id.lvLista)

        banderas = cargar_lista()
        this.recarga()


        lvDatos.setOnItemClickListener() { parent, view, position, id ->
            Toast.makeText(
                this, "Yo soy de " +
                        parent.getItemAtPosition(position).toString(), Toast.LENGTH_SHORT
            ).show()
        }

        registerForContextMenu(lvDatos)

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val toast: Toast
        when (item.itemId) {
            R.id.recargar -> {
                banderas.clear()
                banderas = cargar_lista()
                this.recarga()
            }
            R.id.limpiar -> {
                banderas.clear()
                this.recarga()
            }
        }
        return true
    }

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        menuInflater.inflate(R.menu.context_menu, menu)
        val info = menuInfo as AdapterContextMenuInfo
        val posicion = info.position
        menu?.setHeaderTitle(banderas[posicion].nombre)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        val info = item.menuInfo as AdapterContextMenuInfo
        val posicion = info.position

        return when (item.itemId) {
            R.id.eliminar -> {
                val dialogo = AlertDialog.Builder(this)
                    .setTitle("Eliminar " + banderas[posicion].nombre)
                    .setMessage("¿Estás seguro de que quieres eliminar " + banderas[posicion].nombre + "?")
                    .setPositiveButton("ACEPTAR")
                    { dialogInterface, i ->
                        banderas.remove(banderas[posicion])
                        this.recarga()
                    }
                    .setNegativeButton("CANCELAR") { dialogInterface, i -> dialogInterface.cancel() }
                    .create()
                dialogo.show()
                true
            }
            else -> super.onContextItemSelected(item)
        }
    }


    fun recarga() {
        val adapter = MyAdapter(
            this,
            R.layout.list_view, banderas
        )
        val listView1 = findViewById<ListView>(R.id.lvLista)
        listView1.cacheColorHint = 0
        listView1.adapter = adapter
    }


    fun cargar_lista(): MutableList<Comunidad> {
        val imagenes = intArrayOf(
            R.drawable.andalucia,
            R.drawable.aragon,
            R.drawable.asturias,
            R.drawable.baleares,
            R.drawable.canarias,
            R.drawable.cantabria,
            R.drawable.castillaleon,
            R.drawable.castillamancha,
            R.drawable.catalunya,
            R.drawable.ceuta,
            R.drawable.extremadura,
            R.drawable.galicia,
            R.drawable.larioja,
            R.drawable.madrid,
            R.drawable.melilla,
            R.drawable.murcia,
            R.drawable.navarra,
            R.drawable.paisvasco,
            R.drawable.valencia
        )
        val items = arrayOf(
            "Andalucía", "Aragón", "Asturias", "Baleares",
            "Canarias", "Cantabria", "Castilla y León", "Castilla-La Mancha",
            "Cataluña", "Ceuta", "Extremadura", "Galicia", "La Rioja", "Madrid", "Melilla",
            "Murcia", "Navarra", "País Vasco", "Comunidad Valenciana"
        )
        for (i in items.indices) {
            val comunidad = Comunidad(items[i], imagenes[i])
            banderas.add(comunidad)
        }
        return banderas
    }

}
