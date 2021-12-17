package com.chema.desafio2.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.chema.desafio2.Api.AulasApi
import com.chema.desafio2.Api.PersonaApi
import com.chema.desafio2.Api.ServiceBuilder
import com.chema.desafio2.LoginActivity
import com.chema.desafio2.NewAulaActivity
import com.chema.desafio2.R
import com.chema.desafio2.modelo.ActualUser
import com.chema.desafio2.modelo.Aula
import com.chema.desafio2.modelo.Persona
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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


        holder.itemView.setOnClickListener {
            modif(aulas[position])
            //Toast.makeText(context, aulas[position].IdAula, Toast.LENGTH_SHORT).show()
        }

        holder.itemView.setOnLongClickListener(View.OnLongClickListener {
            AlertDialog.Builder(context).setTitle(R.string.eliminarAula)
                .setPositiveButton(R.string.aceptar) { view, _ ->
                    //elimina tarea
                    check_delete_aula(aulas[position])
                    aulas.remove(aulas[position])
                    notifyDataSetChanged()
                    view.dismiss()
                }.setNegativeButton(R.string.cancelar) { view, _ ->//cancela
                    view.dismiss()
                }.create().show()
            false
        })


    }
    override fun getItemCount(): Int {
        return aulas.size
    }


    fun modif(aula: Aula){
        var myIntent = Intent(context, NewAulaActivity::class.java)
        ActualUser.aulaSeleccionada = aula
        ActualUser.modificandoAula = true
        context.startActivity(myIntent)
    }

    fun check_delete_aula(aula: Aula){
        val request = ServiceBuilder.buildService(AulasApi::class.java)
        val call = request.borrarAula(aula.IdAula.toString())
        call.enqueue(object : Callback<ResponseBody> {

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {

                if (response.code() == 200) {
                    Toast.makeText(context, "Registro eliminado con éxito",Toast.LENGTH_SHORT).show()
                }
                else {
                    Toast.makeText(context, "Algo ha fallado en el borrado",Toast.LENGTH_LONG).show()
                }
            }
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Toast.makeText(context, "Algo ha fallado en la conexión.",Toast.LENGTH_LONG).show()
            }
        })
    }






    //-------------------MyViewHolder--------------------------------------------//
    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var id: TextView = itemView.findViewById<View>(R.id.txtIdAula) as TextView
        var nombre: TextView = itemView.findViewById<View>(R.id.txtNombreAula) as TextView
        var desc: TextView = itemView.findViewById<View>(R.id.txtDescripcionAula) as TextView
        var prof: TextView = itemView.findViewById<View>(R.id.txt_profesor_item_aula) as TextView

        fun bind(aula: Aula, pos: Int, aulasAdapter: AdapterAulas){
            id.setText(aula.IdAula.toString())
            nombre.setText(aula.Nombre.toString())
            desc.setText(aula.Descripcion.toString())
            prof.setText("Profesor encargado ${aula.NombreProfesor.toString()}")
        }
    }
}