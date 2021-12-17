package com.chema.desafio2.adapters

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.chema.desafio2.Api.AulasApi
import com.chema.desafio2.Api.PersonaApi
import com.chema.desafio2.Api.ServiceBuilder
import com.chema.desafio2.R
import com.chema.desafio2.SignUpActivity
import com.chema.desafio2.modelo.ActualUser
import com.chema.desafio2.modelo.Persona
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AdapterProfesores (
    private val context: AppCompatActivity,
    private val profesores: ArrayList<Persona>,
    private val profesorSelec: TextView?
) : RecyclerView.Adapter<AdapterProfesores.ViewHolderProf>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderProf {
        return AdapterProfesores.ViewHolderProf(
            LayoutInflater.from(context).inflate(R.layout.profesor_item_card, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolderProf, position: Int) {
        val prof = profesores[position]
        holder.txt_nombre.text = "Nombre del Profesor: ${prof.Nombre}"
        holder.txt_dni.text = "DNI del Profesor: ${prof.DNI}"
        holder.txt_tlf.text = "Telefono del Profesor: ${prof.Tfno}"


        //MODIFICAR
        holder.itemView.setOnClickListener {
            profesorSelec?.text = profesores[position].Nombre


        }

        holder.itemView.setOnLongClickListener(View.OnLongClickListener {
            AlertDialog.Builder(context).setTitle("Opciones")
                .setPositiveButton("Modificar") { view, _ ->
                    mod(profesores[position])
                    view.dismiss()
                }.setNegativeButton("Eliminar") { view, _ ->//cancela
                    comprueba_delete(profesores[position])
                    view.dismiss()
                }.create().show()
            false
        })
    }

    override fun getItemCount(): Int {
        return profesores.size
    }

    fun mod(persona: Persona){
        val intent = Intent(context, SignUpActivity::class.java)

        ActualUser.modificando = true
        ActualUser.actualUserModif = persona

        context.startActivity(intent)
    }

    fun comprueba_delete(persona: Persona){
        AlertDialog.Builder(context).setTitle(R.string.eliminarperfil)
            .setPositiveButton(R.string.aceptar) { view, _ ->

                check_delete_profesor(persona)
                profesores.remove(persona)
                notifyDataSetChanged()
                view.dismiss()
            }.setNegativeButton(R.string.cancelar) { view, _ ->//cancela

                view.dismiss()
            }.create().show()
    }
    fun check_delete_profesor(persona: Persona){

        val request = ServiceBuilder.buildService(PersonaApi::class.java)
        val call = request.borrarUsuario(persona.Nombre.toString())
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
    class ViewHolderProf(view: View) : RecyclerView.ViewHolder(view) {

        val txt_nombre = view.findViewById<TextView>(R.id.txt_Nombre_profesor)
        val txt_dni = view.findViewById<TextView>(R.id.txtDniProfesor)
        val txt_tlf = view.findViewById<TextView>(R.id.txtTelefonoProfesor)

    }
}