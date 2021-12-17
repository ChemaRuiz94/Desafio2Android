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
import com.chema.desafio2.R
import com.chema.desafio2.modelo.ActualUser
import com.chema.desafio2.modelo.Persona

class AdapterProfesores (
    private val context: AppCompatActivity,
    private val profesores: ArrayList<Persona>,
    private val profesorSelec: TextView
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


        //MODIFICAR TAREA
        holder.itemView.setOnClickListener {
            profesorSelec.text = profesores[position].Nombre
            //ActualUser.profesorNewAula = profesores[position]
        }




    }

    override fun getItemCount(): Int {
        return profesores.size
    }

    class ViewHolderProf(view: View) : RecyclerView.ViewHolder(view) {

        val txt_nombre = view.findViewById<TextView>(R.id.txt_Nombre_profesor)
        val txt_dni = view.findViewById<TextView>(R.id.txtDniProfesor)
        val txt_tlf = view.findViewById<TextView>(R.id.txtTelefonoProfesor)

    }
}