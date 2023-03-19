package activities

import adapters.MyAdapter
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextMenu
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ListView
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import com.example.ud7cp2.R
import models.Comunidad

class MainActivity : AppCompatActivity() {

    private lateinit var intentLaunch: ActivityResultLauncher<Intent>
    private var nombre: String? = ""
    private var imagen: Int? = 0
    var banderas: MutableList<Comunidad> = mutableListOf()
    private var adapterListView: MyAdapter? = null
    private var posicion: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val lvDatos: ListView = findViewById(R.id.lvLista)
        adapterListView = MyAdapter(this, R.layout.list_view, banderas)

        banderas = cargar_lista()
        lvDatos.adapter = adapterListView


        lvDatos.setOnItemClickListener() { parent, view, position, id ->
            Toast.makeText(
                this, "Yo soy de " +
                        parent.getItemAtPosition(position).toString(), Toast.LENGTH_SHORT
            ).show()
        }

        registerForContextMenu(lvDatos)

        intentLaunch = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result: ActivityResult ->
            if (result.resultCode == RESULT_OK) {
                nombre = result.data?.extras?.getString("nombre").toString()
                println(nombre)
                imagen = result.data?.extras?.getInt("imagen")
                banderas[posicion!!].nombre = nombre!!
                adapterListView!!.notifyDataSetChanged()
            }
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.recargar -> {
                banderas.clear()
                banderas = cargar_lista()
                adapterListView?.notifyDataSetChanged()
            }
            R.id.limpiar -> {
                banderas.clear()
                adapterListView?.notifyDataSetChanged()
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
        val info = menuInfo as AdapterView.AdapterContextMenuInfo
        posicion = info.position
        menu?.setHeaderTitle(banderas[posicion!!].nombre)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        val info = item.menuInfo as AdapterView.AdapterContextMenuInfo
        posicion = info.position

        return when (item.itemId) {
            R.id.eliminar -> {
                val dialogo = AlertDialog.Builder(this)
                    .setTitle("Eliminar " + banderas[posicion!!].nombre)
                    .setMessage("¿Estás seguro de que quieres eliminar " + banderas[posicion!!].nombre + "?")
                    .setPositiveButton("ACEPTAR")
                    { dialogInterface, i ->
                        banderas.remove(banderas[posicion!!])
                        adapterListView?.notifyDataSetChanged()                    }
                    .setNegativeButton("CANCELAR") { dialogInterface, i -> dialogInterface.cancel() }
                    .create()
                dialogo.show()
                true
            }
            R.id.editar -> {
                val intent = Intent(this, Activity2::class.java)
                intent.putExtra("nombre", banderas[posicion!!].nombre)
                intent.putExtra("imagen", banderas[posicion!!].imagen)
                intentLaunch.launch(intent)
                true
            }
            else -> super.onContextItemSelected(item)
        }
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