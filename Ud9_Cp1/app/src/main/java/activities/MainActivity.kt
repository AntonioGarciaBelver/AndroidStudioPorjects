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
import com.example.ComunidadCRUD
import com.example.ud9_cp1.R
import models.Comunidad

class MainActivity : AppCompatActivity() {

    private lateinit var intentLaunch: ActivityResultLauncher<Intent>
    private var nombre: String? = ""
    private var imagen: Int? = 0
    var banderas: MutableList<Comunidad> = mutableListOf()
    private lateinit var adapterListView: MyAdapter
    private var posicion: Int = 0
    private lateinit var listview: ListView
    val comunidadCrud= ComunidadCRUD(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        createBDD()
        banderas = comunidadCrud.getComunidades()
        inicializarAdapter()

        listview.setOnItemClickListener() { parent, view, position, id ->
            Toast.makeText(
                this, "Yo soy de " +
                        parent.getItemAtPosition(position).toString(), Toast.LENGTH_SHORT
            ).show()
        }

        intentLaunch = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result: ActivityResult ->
            if (result.resultCode == RESULT_OK) {
                nombre = result.data?.extras?.getString("nombre").toString()
                banderas[posicion].nombre = nombre.toString()

                comunidadCrud.updateComunidad(banderas[posicion!!])
                adapterListView!!.notifyDataSetChanged()
            }
        }

    }

    private fun inicializarAdapter() {

        listview = findViewById(R.id.lvLista)
        adapterListView = MyAdapter(this, R.layout.list_view, banderas)
        listview.adapter = adapterListView
        registerForContextMenu(listview)
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.recargar -> {
                return recargarBDD()
            }
            R.id.limpiar -> {
                banderas.clear()
                adapterListView.notifyDataSetChanged()
                Toast.makeText(this, "Has limpiado la pantalla", Toast.LENGTH_SHORT).show()
                return true
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
                        comunidadCrud.deleteComunidad(banderas[posicion!!])
                        adapterListView?.notifyDataSetChanged()
                        recargarBDD()}
                    .setNegativeButton("CANCELAR") { dialogInterface, i -> dialogInterface.cancel() }
                    .create()
                dialogo.show()

                true


            }
            R.id.editar -> {
                var nombre: String = banderas[info.position].nombre
                var bandera: Int = banderas[info.position].bandera
                val intent = Intent(this, Activity2::class.java)
                intent.putExtra("nombre", nombre)
                intent.putExtra("imagen", bandera)
                intentLaunch.launch(intent)

                true
            }
            else -> super.onContextItemSelected(item)
        }
    }

    fun createBDD() {

        //Comprueba que la base de datos no este creada, en ese caso, lanzamos la funcion que carga
        // todas las comunidades y las ingresamos una a una con comunidadCrud en la BDD
        if (comunidadCrud.getComunidades().size == 0) {
            for (comunidad in cargar_lista()) {

                comunidadCrud.newComunidad(comunidad)
            }
        }
    }

    private fun recargarBDD(): Boolean {
        banderas.clear()
        banderas.addAll(comunidadCrud.getComunidades())
        adapterListView.notifyDataSetChanged()
        Toast.makeText(this, "Has recargado la lista", Toast.LENGTH_SHORT).show()
        return true
    }

    fun cargar_lista(): MutableList<Comunidad> {
        val listaComunidades = mutableListOf<Comunidad>(
            Comunidad(0, "Andalucia", R.drawable.andalucia),
            Comunidad(1, "Aragón", R.drawable.aragon),
            Comunidad(2, "Asturias", R.drawable.asturias),
            Comunidad(3, "Baleares", R.drawable.baleares),
            Comunidad(4, "Canarias", R.drawable.canarias),
            Comunidad(5, "Cantabria", R.drawable.cantabria),
            Comunidad(6, "Castilla y León", R.drawable.castillaleon),
            Comunidad(7, "Castilla la mancha", R.drawable.castillamancha),
            Comunidad(8, "Cataluña", R.drawable.catalunya),
            Comunidad(9, "Ceuta", R.drawable.ceuta),
            Comunidad(10, "Extremadura", R.drawable.extremadura),
            Comunidad(11, "Galicia", R.drawable.galicia),
            Comunidad(12, "La Rioja", R.drawable.larioja),
            Comunidad(13, "Madrid", R.drawable.madrid),
            Comunidad(14, "Melilla", R.drawable.melilla),
            Comunidad(15, "Murcia", R.drawable.murcia),
            Comunidad(16, "Navarra", R.drawable.navarra),
            Comunidad(17, "Pais Vasco", R.drawable.paisvasco),
            Comunidad(18, "Valencia", R.drawable.valencia)
        )
        return listaComunidades
    }


}