package activities

import adapters.GridViewAdapter
import adapters.ListViewAdapter
import adapters.MyAdapter
import android.os.Bundle
import android.view.*
import android.widget.AdapterView.OnItemClickListener
import android.widget.GridView
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.ud6_cp1.R
import models.Frutas

class MainActivity : AppCompatActivity() {

    private var stubGrid: ViewStub? = null
    private var stubList: ViewStub? = null
    private var listView: ListView? = null
    private var gridView: GridView? = null
    private var listViewAdapter: ListViewAdapter? = null
    private var gridViewAdapter: GridViewAdapter? = null
    lateinit var frutas: MutableList<Frutas>
    var currentViewMode = 0

    val VIEW_MODE_LISTVIEW = 0
    val VIEW_MODE_GRIDVIEW = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        stubList = findViewById(R.id.stub_list) as ViewStub
        stubGrid = findViewById(R.id.stub_grid) as ViewStub

        //Inflate ViewStub before get view
        stubList!!.inflate()
        stubGrid!!.inflate()
        listView = findViewById(R.id.mylistview) as ListView
        gridView = findViewById(R.id.mygridview) as GridView

        //get list of product
        frutas = cargar_lista()
        this.recarga()

        //Get current view mode in share reference
        val sharedPreferences = getSharedPreferences("ViewMode", MODE_PRIVATE)
        currentViewMode = sharedPreferences.getInt(
            "currentViewMode",
            VIEW_MODE_LISTVIEW
        ) //Default is view listview
        //Register item lick
        listView!!.onItemClickListener = onItemClick
        gridView!!.onItemClickListener = onItemClick
        switchView()
    }

    private fun switchView() {
        if (VIEW_MODE_LISTVIEW == currentViewMode) {
            //Display listview
            stubList!!.visibility = View.VISIBLE
            //Hide gridview
            stubGrid!!.visibility = View.GONE
        } else {
            //Hide listview
            stubList!!.visibility = View.GONE
            //Display gridview
            stubGrid!!.visibility = View.VISIBLE
        }
        setAdapters()
    }

    private fun setAdapters() {
        if (VIEW_MODE_LISTVIEW == currentViewMode) {
            listViewAdapter = ListViewAdapter(this, R.layout.list_item, frutas!!)
            listView!!.adapter = listViewAdapter
        } else {
            gridViewAdapter = GridViewAdapter(this, R.layout.grid_item, frutas!!)
            gridView!!.adapter = gridViewAdapter
        }
    }

    fun recarga() {

        val adapter = MyAdapter(
            this,
            R.layout.grid_item, frutas
        )
        val listView1 = findViewById<GridView>(R.id.mygridview)
        listView1.cacheColorHint = 0
        listView1.adapter = adapter


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

    var onItemClick =
        OnItemClickListener { parent, view, position, id -> //Do any thing when user click to item
            Toast.makeText(
                this, "La mejor fruta de " + frutas[position].origen + " es " +
                        parent.getItemAtPosition(position).toString(), Toast.LENGTH_SHORT
            ).show()
        }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_menu_1 -> {
                currentViewMode = if (VIEW_MODE_LISTVIEW == currentViewMode) {
                    VIEW_MODE_GRIDVIEW
                } else {
                    VIEW_MODE_LISTVIEW
                }
                //Switch view
                switchView()
                //Save view mode in share reference
                val sharedPreferences = getSharedPreferences("ViewMode", MODE_PRIVATE)
                val editor = sharedPreferences.edit()
                editor.putInt("currentViewMode", currentViewMode)
                editor.commit()
            }
        }
        return true
    }

}
