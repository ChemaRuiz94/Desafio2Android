package com.chema.desafio2.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chema.desafio2.Api.PersonaApi
import com.chema.desafio2.Api.ServiceBuilder
import com.chema.desafio2.R
import com.chema.desafio2.adapters.AdapterAulas
import com.chema.desafio2.adapters.AdapterProfesores
import com.chema.desafio2.modelo.Aula
import com.chema.desafio2.modelo.Persona
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyFragmentProfesores : Fragment(){

    var profes: ArrayList<Persona> = ArrayList<Persona>()
    private lateinit var miAdapter: AdapterProfesores
    lateinit var miRecyclerView: RecyclerView
    private var contexto : Context? = null
    private var txt_ej : TextView? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        //return super.onCreateView(inflater, container, savedInstanceState)
        val view = inflater?.inflate(R.layout.fragment_profesores,container,false)

        txt_ej = null
        contexto = view.context

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getProfes(view)
    }

    fun getProfes(view: View){
        val request = ServiceBuilder.buildService(PersonaApi::class.java)
        val call = request.getPersonas()

        call.enqueue(object : Callback<MutableList<Persona>> {
            override fun onResponse(call: Call<MutableList<Persona>>, response: Response<MutableList<Persona>>) {

                if (response.isSuccessful){
                    for (post in response.body()!!) {
                        profes.add(Persona(post.DNI,post.Nombre,post.Clave,post.Tfno))
                    }
                    cargarRV(view)
                }else{
                    Toast.makeText(contexto, "Error de conexion al traer las personas", Toast.LENGTH_SHORT).show()
                }
            }
            override fun onFailure(call: Call<MutableList<Persona>>, t: Throwable) {
                Toast.makeText(contexto, "Error de conexion", Toast.LENGTH_SHORT).show()
            }
        })
    }

    fun cargarRV(view: View){

        miRecyclerView = view.findViewById(R.id.rv_profesores)
        miRecyclerView.setHasFixedSize(true)
        miRecyclerView.layoutManager = LinearLayoutManager(contexto!!)
        miAdapter = AdapterProfesores(contexto!! as AppCompatActivity, profes, txt_ej)
        miRecyclerView.adapter = miAdapter

    }
}