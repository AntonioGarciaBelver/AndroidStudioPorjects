package activities

import adapters.MyAdapter
import android.content.Intent
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
import androidx.appcompat.app.AppCompatActivity
import com.example.ComunidadCRUD
import com.example.ud10_cp1.R
import com.example.ud10_cp1.databinding.ActivityMapsBinding
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import models.Comunidad

class MainActivity : AppCompatActivity(){

    private lateinit var intentLaunch: ActivityResultLauncher<Intent>
    private var nombre: String? = ""
    private var imagen: Int? = 0
    var banderas: MutableList<Comunidad> = mutableListOf()
    private lateinit var adapterListView: MyAdapter
    private var posicion: Int = 0
    private lateinit var listview: ListView
    val comunidadCrud= ComunidadCRUD(this)
    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding
    private var latitud: Double = 0.0
    private var longitud: Double = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        createBDD()
        banderas = comunidadCrud.getComunidades()
        inicializarAdapter()

        listview.setOnItemClickListener() { parent, view, position, id ->
            var comunidad = banderas[position]
            latitud = comunidad.latitud
            longitud = comunidad.longitud
            val intent = Intent(this@MainActivity, MapsActivity::class.java)
            intent.putExtra("latitud", latitud)
            intent.putExtra("longitud", longitud)
            startActivity(intent)
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
            Comunidad(0, "Andalucia","Sevilla", R.drawable.andalucia, 37.388323905121034, -5.983554497682638, R.drawable.andalucia_icon, "8.427.000"),
            Comunidad(1, "Aragón","Zaragoza", R.drawable.aragon,41.647417745081825,-0.8819883708764259,R.drawable.aragon_icon, "1.321.000"),
            Comunidad(2, "Asturias","Oviedo", R.drawable.asturias,43.36219817390104,-5.848446132018383,R.drawable.asturias_icon, "1.022.000"),
            Comunidad(3, "Baleares","Palma de Mallorca", R.drawable.baleares,39.565537945700804,2.6424580664856547,R.drawable.baleares_icon, "1.188.000"),
            Comunidad(4, "Canarias", "Santa Cruz de Tenerife", R.drawable.canarias,28.464713834433553,-16.253383844670363,R.drawable.canarias_icon, "2.207.000"),
            Comunidad(5, "Cantabria","Santander", R.drawable.cantabria,43.46328612888761,-3.822949681525621,R.drawable.cantabria_icon, "581.641"),
            Comunidad(6, "Castilla y León","Valladolid", R.drawable.castillaleon,41.65143369715255,-4.730192938752998,R.drawable.castillaleon_icon, "2.408.000"),
            Comunidad(7, "Castilla la mancha", "Toledo", R.drawable.castillamancha,39.863201457065045,-4.024895708344843, R.drawable.castillamancha_icon, "2.035.000"),
            Comunidad(8, "Cataluña","Barcelona", R.drawable.catalunya,41.38749532740227,2.1552935491948384, R.drawable.catalunya_icon, "7.566.000"),
            Comunidad(9, "Ceuta","Ceuta", R.drawable.ceuta,35.88961141596787,-5.3211008382444716, R.drawable.ceuta_icon, "84.829"),
            Comunidad(10, "Extremadura","Merida", R.drawable.extremadura,38.91835713152803,-6.343166285327827, R.drawable.extremadura_icon, "1.065.000"),
            Comunidad(11, "Galicia","Santiago de Compostela", R.drawable.galicia,42.87762487364634,-8.54511308124891, R.drawable.galicia_icon, "2.700.000"),
            Comunidad(12, "La Rioja","Logroño", R.drawable.larioja,42.463706722015225,-2.44647486154823,R.drawable.larioja_icon, "313.571"),
            Comunidad(13, "Madrid","Madrid", R.drawable.madrid,40.4250722410217,-3.693297918432954, R.drawable.madrid_icon, "3.223.000"),
            Comunidad(14, "Melilla","Melilla", R.drawable.melilla,35.29188203272616,-2.935772245066959, R.drawable.melilla_icon, "84.689"),
            Comunidad(15, "Murcia","Murcia", R.drawable.murcia,37.991350964896725,-1.1285490523805437, R.drawable.murcia_icon, "447.182"),
            Comunidad(16, "Navarra","Pamplona", R.drawable.navarra,42.811463197791745,-1.6479633958229563, R.drawable.navarra_icon, "649.946"),
            Comunidad(17, "Pais Vasco","Vitoria", R.drawable.paisvasco,42.85321471809858,-2.6739886474249763, R.drawable.paisvasco_icon, "2.178.000"),
            Comunidad(18, "Valencia","Valencia", R.drawable.valencia,39.468484046115215,-0.3666629520384152, R.drawable.valencia_icon, "4.946.000")
        )
        return listaComunidades
    }

    private var latitude = mutableListOf<Double>(
        //Melilla
        35.29188203272616,
        //Murcia
        37.991350964896725,
        //Pamplona
        42.811463197791745,
        //Vitoria
        42.85321471809858,
        //Valencia
        39.468484046115215,
    )
    private var longitude = mutableListOf<Double>(
        //Sevilla
        -5.983554497682638,
        //Zaragoza
        -0.8819883708764259,
        //Oviedo
        -5.848446132018383,
        //Palma Mallorca
        2.6424580664856547,
        //Sta Cruz Tenerife
        -16.253383844670363,
        //Santander
        -3.822949681525621,
        //Valladolid
        -4.730192938752998,
        //Toledo
        -4.024895708344843,
        //Barcelona
        2.1552935491948384,
        //Ceuta
        -5.3211008382444716,
        //Merida
        -6.343166285327827,
        //Santiago de Compostela
        -8.54511308124891,
        //Logroño
        -2.44647486154823,
        //Madrid
        -3.693297918432954,
        //Melilla
        -2.935772245066959,
        //Murcia
        -1.1285490523805437,
        //Pamplona
        -1.6479633958229563,
        //Vitoria
        -2.6739886474249763,
        //Valencia
        -0.3666629520384152
    )

}