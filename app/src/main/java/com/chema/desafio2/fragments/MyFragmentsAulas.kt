package com.chema.desafio2.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chema.desafio2.Api.AulasApi
import com.chema.desafio2.Api.ServiceBuilder
import com.chema.desafio2.R
import com.chema.desafio2.adapters.AdapterAulas
import com.chema.desafio2.modelo.Aula
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyFragmentsAulas():Fragment() {

    var aulas: ArrayList<Aula> = ArrayList<Aula>()
    private lateinit var miAdapter: AdapterAulas
    lateinit var miRecyclerView: RecyclerView
    private var contexto : Context? = null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        //return super.onCreateView(inflater, container, savedInstanceState)
        val view = inflater?.inflate(R.layout.fragment_aula,container,false)
        contexto = view.context

        //recyclerView = findViewById<RecyclerView>(R.id.RVListaPersonas)
        //val linearLayoutManager = LinearLayoutManager(applicationContext)
        //recyclerView.layoutManager = linearLayoutManager

        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //val bun = requireActivity().intent.extras!!

        getAulas(view)
        //cargarRV(view)
    }



    fun getAulas(view: View){
        aulas.clear()
        val request = ServiceBuilder.buildService(AulasApi::class.java)
        val call = request.getAulas()

        call.enqueue(object : Callback<MutableList<Aula>> {
            override fun onResponse(call: Call<MutableList<Aula>>, response: Response<MutableList<Aula>>) {


                if (response.isSuccessful){

                    for (post in response.body()!!) {
                        aulas.add(Aula(post.IdAula,post.Nombre,post.Descripcion))
                        Log.d("Che",post.Nombre.toString())
                    }
                    cargarRV(view)
                    //Toast.makeText(context,"Aulas traidas correctamente",Toast.LENGTH_SHORT).show()
                }else{

                    Toast.makeText(context,"Fallo de al traer las aulas",Toast.LENGTH_SHORT).show()

                }
            }
            override fun onFailure(call: Call<MutableList<Aula>>, t: Throwable) {
                Toast.makeText(context,"Fallo de conexion",Toast.LENGTH_SHORT).show()
            }
        })
    }

    fun cargarRV(view: View){

        miRecyclerView = view.findViewById(R.id.rv_aulas)
        miRecyclerView.setHasFixedSize(true)
        miRecyclerView.layoutManager = LinearLayoutManager(contexto!!)
        miAdapter = AdapterAulas(contexto!!, aulas)
        miRecyclerView.adapter = miAdapter

    }
}