package com.example.ud5cp2

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView

class MyAdapter(
    private val context: Context,
    private val layout: Int,
    private val nombres:MutableList<Comunidad>
):
    BaseAdapter() {
    override fun getCount(): Int {
        return nombres.size;
    }

    override fun getItem(p0: Int): Any {
        return nombres[p0]
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }

    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        var vista = p1
        val holder: ViewHolder
        if(p1 == null) {
            val layoutInflater = LayoutInflater.from(context)
            vista = layoutInflater.inflate(R.layout.list_view, null)

            holder = ViewHolder()
            holder.texto = vista.findViewById(R.id.textView)
            holder.foto = vista.findViewById(R.id.imageView)
            vista.tag = holder
        }
        else{
            holder = p1.tag as ViewHolder
        }
        val nombre = nombres[p0]

        holder.texto!!.text = nombre.nombre
        holder.foto!!.setImageDrawable(context.getDrawable(nombre.imagen))

        return vista!!

    }

    internal class ViewHolder{

        var foto: ImageView? = null
        var texto: TextView? = null
    }

}