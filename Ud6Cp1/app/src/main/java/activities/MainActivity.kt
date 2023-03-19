package activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextMenu
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView.AdapterContextMenuInfo
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.ud6cp1.R
import adapters.MyAdapter
import android.widget.GridView
import android.widget.ListView
import com.example.ud6cp1.databinding.ActivityMainBinding
import models.Frutas

class MainActivity : AppCompatActivity() {

    private var contador = 0
    private lateinit var listView: ListView
    private lateinit var gridView: GridView
    var grid: MenuItem? = null
    var list: MenuItem? = null
    private var adapterListView: MyAdapter? = null
    private var adapterGridView: MyAdapter? = null

    var frutas: MutableList<Frutas> = mutableListOf()

    val VIEW_MODE_LISTVIEW = 0
    val VIEW_MODE_GRIDVIEW = 1

    val imagenes = intArrayOf(
        R.drawable.cereza,
        R.drawable.frambuesa,
        R.drawable.fresa,
        R.drawable.manzana,
        R.drawable.naranja,
        R.drawable.pera,
        R.drawable.platano,
        R.drawable.fruta_nueva,
        R.drawable.sandia,
    )
    val origen = arrayOf(
        "Galicia", "Barcelona", "Huelva",
        "Madrid", "Valencia", "Zaragoza",
        "Gran Canaria", "Desconocido"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        frutas = cargar_lista()

        listView = findViewById(R.id.lvVista)
        gridView = findViewById(R.id.gvLista)

        adapterListView = MyAdapter(this, R.layout.list_item, frutas)
        adapterGridView = MyAdapter(this, R.layout.grid_item, frutas)

        listView.adapter = adapterListView
        gridView.adapter = adapterGridView

        gridView.setOnItemClickListener() { parent, view, position, id ->
            Toast.makeText(
                this, "La mejor fruta de " + frutas[position].origen + " es " +
                        parent.getItemAtPosition(position).toString(), Toast.LENGTH_SHORT
            ).show()
        }

        registerForContextMenu(gridView)

    }

    private fun switchView(opcion: Int) {
        if (opcion == VIEW_MODE_LISTVIEW) {
            //Display listview
            listView.visibility = View.VISIBLE
            //Hide gridview
            gridView.visibility = View.GONE
            grid?.setVisible(true)
            list?.setVisible(false)
        } else{
            //Hide listview
            listView.visibility = View.GONE
            //Display gridview
            gridView.visibility = View.VISIBLE
            grid?.setVisible(false)
            list?.setVisible(true)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
        grid = menu?.findItem(R.id.grid)
        list = menu?.findItem(R.id.list)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.add_item -> {
                frutas.add(frutas.size, Frutas("Nueva nº " + ++contador, imagenes[7], origen[7]))

                adapterListView?.notifyDataSetChanged()
                adapterGridView?.notifyDataSetChanged()
            }
            R.id.grid ->{
                registerForContextMenu(gridView)
                switchView(VIEW_MODE_GRIDVIEW)
                true
            }
            R.id.list ->{
                listView.setOnItemClickListener() { parent, view, position, id ->
                    Toast.makeText(
                        this, "La mejor fruta de " + frutas[position].origen + " es " +
                                parent.getItemAtPosition(position).toString(), Toast.LENGTH_SHORT
                    ).show()
                }
                registerForContextMenu(listView)
                switchView(VIEW_MODE_LISTVIEW)

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
        menu?.setHeaderTitle(frutas[posicion].nombre)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        val info = item.menuInfo as AdapterContextMenuInfo
        val posicion = info.position

        return when (item.itemId) {
            R.id.eliminar -> {
                val dialogo = AlertDialog.Builder(this)
                    .setTitle("Eliminar " + frutas[posicion].nombre)
                    .setMessage("¿Estás seguro de que quieres eliminar " + frutas[posicion].nombre + "?")
                    .setPositiveButton("ACEPTAR")
                    { dialogInterface, i ->
                        frutas.remove(frutas[posicion])
                        adapterListView?.notifyDataSetChanged()
                        adapterGridView?.notifyDataSetChanged()
                    }
                    .setNegativeButton("CANCELAR") { dialogInterface, i -> dialogInterface.cancel() }
                    .create()
                dialogo.show()
                true
            }
            else -> super.onContextItemSelected(item)
        }
    }

    fun cargar_lista(): MutableList<Frutas> {
        val imagenes = intArrayOf(
            R.drawable.cereza,
            R.drawable.frambuesa,
            R.drawable.fresa,
            R.drawable.manzana,
            R.drawable.naranja,
            R.drawable.pera,
            R.drawable.platano,
            R.drawable.fruta_nueva,
            R.drawable.sandia,
        )
        val items = arrayOf(
            "Cereza", "Frambuesa", "Fresa",
            "Manzana", "Naranja", "Pera",
            "Plátano"
        )
        val origen = arrayOf(
            "Galicia", "Barcelona", "Huelva",
            "Madrid", "Valencia", "Zaragoza",
            "Gran Canaria", "Desconocido"
        )
        for (i in items.indices) {
            val fruta = Frutas(items[i], imagenes[i], origen[i])

            frutas.add(fruta)
        }
        return frutas
    }

}
