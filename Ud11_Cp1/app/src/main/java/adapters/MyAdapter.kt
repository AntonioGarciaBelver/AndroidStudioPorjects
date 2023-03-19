package adapters

import android.view.*
import android.view.MenuItem.OnMenuItemClickListener
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.ud11_cp1.R
import com.jlara.ejemplorvcv_kotlin.OnItemClickListener
import models.Fruta

class MyAdapter(private var frutas:MutableList<Fruta>, var listener: OnItemClickListener):
                                            RecyclerView.Adapter<MyAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v=LayoutInflater.from(parent.context).inflate(R.layout.recycler_view_item, parent, false)
        return ViewHolder(v,listener, frutas, this)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item=frutas.get(position)
        holder.tvTitulo.text=item.nombre
        holder.ivFruta.setImageResource(item.imagen)
        holder.tvDescripcion.text=item.descripcion
        holder.tvCantidad.text = item.cantidad.toString()
    }

    override fun getItemCount(): Int {
        return frutas.size
    }

    class ViewHolder(v: View, var listener: OnItemClickListener, private var frutas: MutableList<Fruta>, private var adapter: MyAdapter) : RecyclerView.ViewHolder(v), View.OnClickListener, View.OnCreateContextMenuListener, OnMenuItemClickListener  {
        var tvTitulo:TextView=v.findViewById(R.id.tvTitulo)
        var ivFruta:ImageView=v.findViewById(R.id.ivFruta)
        var tvDescripcion: TextView = v.findViewById(R.id.tvDescripcion)
        var tvCantidad: TextView = v.findViewById(R.id.tvCantidad)


        init {
            v.setOnClickListener(this)
            ivFruta.setOnClickListener {
                listener.onImageClick(it, adapterPosition)
            }
            itemView.setOnCreateContextMenuListener(this)
        }

        override fun onClick(p0: View?) {
            this.listener.onItemClick(p0!!,adapterPosition)
        }

        override fun onCreateContextMenu(
            menu: ContextMenu?,
            v: View?,
            menuInfo: ContextMenu.ContextMenuInfo?
        ) {
            val frutaSeleccionada: Fruta = frutas[adapterPosition]
            menu!!.setHeaderTitle(frutaSeleccionada.nombre)

            val inflater: MenuInflater = MenuInflater(itemView.context)
            inflater.inflate(R.menu.context_menu, menu)
            for (i in 0 until menu.size()){
                menu.getItem(i).setOnMenuItemClickListener(this)
            }
        }

        override fun onMenuItemClick(item: MenuItem): Boolean {
            return when (item.itemId){
                R.id.eliminar -> {
                    frutas.removeAt(adapterPosition)
                    adapter.notifyItemRemoved(adapterPosition)
                    true
                }
                R.id.resetear -> {
                    frutas[adapterPosition].cantidad = 0
                    adapter.notifyItemChanged(adapterPosition)
                    true
                }
                else -> {return false}
            }
        }
    }

}
