package adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.ud6cp1.R
import models.Frutas

class MyAdapter(
    private val context: Context,
    var layout: Int,
    private val frutas:MutableList<Frutas>
):
    BaseAdapter() {
    override fun getCount(): Int {
        return frutas.size;
    }

    override fun getItem(p0: Int): Any {
        return frutas[p0]
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }

    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        var vista = p1
        val holder: ViewHolder
        if(p1 == null) {
            val layoutInflater = LayoutInflater.from(context)
            vista = layoutInflater.inflate(layout, null)

            holder = ViewHolder()
            holder.texto = vista.findViewById(R.id.textView)
            holder.foto = vista.findViewById(R.id.imageView)
            holder.texto2 = vista.findViewById(R.id.textView3)
            vista.tag = holder
        }
        else{
            holder = p1.tag as ViewHolder
        }
        val fruta = frutas[p0]

        holder.texto!!.text = fruta.nombre
        holder.foto!!.setImageDrawable(context.getDrawable(fruta.imagen))
        holder.texto2!!.text = fruta.origen

        return vista!!

    }

    internal class ViewHolder{

        var foto: ImageView? = null
        var texto: TextView? = null
        var texto2: TextView? = null
    }

}