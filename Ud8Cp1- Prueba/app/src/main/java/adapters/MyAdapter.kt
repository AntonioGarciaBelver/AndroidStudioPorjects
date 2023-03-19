package adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.example.ud7cp2.R
import models.Comunidad

class MyAdapter( private val context: Context,
                 private val layout : Int,
                 private val listaComunidades: List<Comunidad>)
    : BaseAdapter() {

    override fun getCount(): Int {
        return listaComunidades.size
    }

    override fun getItem(position: Int): Comunidad {
        return listaComunidades[position]
    }

    override fun getItemId(id: Int): Long {
        return id.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var cView = convertView
        val holder : ViewHolder
        if( cView == null){

            cView = LayoutInflater.from(context).inflate(layout,null)
            holder = ViewHolder(

                cView.findViewById(R.id.imageView),
                cView.findViewById(R.id.textView)

            )
            cView.tag = holder

        } else{
            holder = cView.tag as ViewHolder
        }

        val paisActual = listaComunidades[position]
        holder.imgBanderaComunidad.setImageResource(paisActual.imagen)
        holder.txtComunidad.setText(paisActual.nombre)

        return cView!!
    }

    internal class ViewHolder(
        var imgBanderaComunidad: ImageView,
        var txtComunidad: TextView


    )

}
