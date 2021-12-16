package com.chema.desafio2.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.chema.desafio2.R
import com.chema.desafio2.modelo.Aula

class AdapterAulas (private var context: Context,
                    private var aulas : ArrayList<Aula>
) :
    RecyclerView.Adapter<AdapterAulas.MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.aula_item_card, parent, false)
        return MyViewHolder(v)
    }
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val item = aulas[position]
        holder.bind(item, position, this)

       /*
        holder.id.text = aulas[position].IdAula
        holder.nombre.text = aulas[position].Nombre
        holder.desc.text = aulas[position].Descripcion

        holder.itemView.setOnClickListener {
            Toast.makeText(context, aulas[position].IdAula, Toast.LENGTH_SHORT).show()
        }
        */
    }
    override fun getItemCount(): Int {
        return aulas.size
    }
    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var id: TextView = itemView.findViewById<View>(R.id.txtIdAula) as TextView
        var nombre: TextView = itemView.findViewById<View>(R.id.txtNombre) as TextView
        var desc: TextView = itemView.findViewById<View>(R.id.txtDescripcion) as TextView

        fun bind(aula: Aula, pos: Int, aulasAdapter: AdapterAulas){
            id.setText(aula.IdAula.toString())
            nombre.setText(aula.Nombre.toString())
            desc.setText(aula.Descripcion.toString())
        }
    }
}