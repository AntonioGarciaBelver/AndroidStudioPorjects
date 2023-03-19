package com.example.ud11_cp1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import adapters.MyAdapter
import com.jlara.ejemplorvcv_kotlin.OnItemClickListener
import models.Fruta

class MainActivity : AppCompatActivity() {
    private lateinit var frutas: MutableList<Fruta>
    private lateinit var mAdapter: RecyclerView.Adapter<MyAdapter.ViewHolder>
    private lateinit var mLayoutManager: RecyclerView.LayoutManager
    private var contador:Int=0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        frutas = this.getAllFrutas()
        val mRecyclerView: RecyclerView = findViewById(R.id.recyclerView)
        mLayoutManager = LinearLayoutManager(this)
        mAdapter = MyAdapter(frutas, object: OnItemClickListener {
            override fun onItemClick(vista: View, position: Int) {
                //sumarFruta(position)
            }
            override fun onImageClick(view: View, position: Int) {
                sumarFruta(position)
            }

        })
        mRecyclerView.adapter=mAdapter
        mRecyclerView.setHasFixedSize(true)
        mRecyclerView.itemAnimator= DefaultItemAnimator()
        mRecyclerView.layoutManager=mLayoutManager

        registerForContextMenu(mRecyclerView)
    }

    private fun getAllFrutas(): MutableList<Fruta> {
        return mutableListOf (
            Fruta("Manzanas","Descripcion de Manzanas", R.drawable.apple_bg, 0),
            Fruta("Platanos","Descripcion de Platanos", R.drawable.banana_bg, 0),
            Fruta("Cerezas","Descripcion de Cerezas", R.drawable.cherry_bg, 0),
            Fruta("Naranjas","Descripcion de Naranjas", R.drawable.orange_bg, 0),
            Fruta("Peras","Descripcion de Peras", R.drawable.pear_bg, 0),
            Fruta("Ciruela","Descripcion de Ciruela", R.drawable.plum_bg, 0)
        )
    }

    private fun addFruta(position: Int) {
        frutas.add(position, Fruta("Plum $contador","Fruta añadida por el usuario", R.drawable.plum_bg, 0))
        mAdapter.notifyItemInserted(position)
        //Para que se vean los elementos añadidos en caso de scroll
        mLayoutManager.scrollToPosition(position)
        contador++
    }

    private fun deleteFruta(position: Int) {
        frutas.removeAt(position)
        mAdapter.notifyItemRemoved(position)
    }

    private fun sumarFruta(position: Int){
        if(frutas[position].cantidad==10){
            frutas[position].cantidad = 0
        }else{
            frutas[position].cantidad += 1
        }

        mAdapter.notifyDataSetChanged()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.btnAnadirFruta -> {
                addFruta(frutas.size)
            }
        }
        return true
    }
}