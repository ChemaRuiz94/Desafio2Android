package com.chema.desafio2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chema.desafio2.Api.AulasApi
import com.chema.desafio2.Api.PersonaApi
import com.chema.desafio2.Api.ServiceBuilder
import com.chema.desafio2.adapters.AdapterProfesores
import com.chema.desafio2.modelo.ActualUser
import com.chema.desafio2.modelo.Aula
import com.chema.desafio2.modelo.Persona
import com.google.android.material.floatingactionbutton.FloatingActionButton
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NewAulaActivity : AppCompatActivity() {


    lateinit var miRecyclerView: RecyclerView
    private lateinit var miAdapterTarea: AdapterProfesores
    private var profes: ArrayList<Persona> = ArrayList<Persona>()


    lateinit var txt_prof_selec : TextView
    lateinit var txt_id_aula : EditText
    lateinit var txt_nomb_aula : EditText
    lateinit var txt_desc_aula : EditText
    lateinit var btn_save : FloatingActionButton

    var contexto = this

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_aula)


        txt_prof_selec = findViewById(R.id.txt_profesor_new_aula)
        txt_nomb_aula = findViewById(R.id.ed_txt_nombre_new_aula)
        txt_id_aula = findViewById(R.id.ed_txt_id_aula_new_aula)
        txt_desc_aula = findViewById(R.id.ed_txt_descricpcion_new_aula)

        btn_save = findViewById(R.id.flt_btn_save_new_aula)

        if(ActualUser.modificandoAula == true){
            rellenar_campos_aula_mod()
        }

        btn_save.setOnClickListener{
            if(check_campos()){
                if(ActualUser.modificandoAula == false){
                    add_aula()
                }else{
                    mod_aula()
                }
            }else{
                Toast.makeText(contexto,getString(R.string.relleneCampos),Toast.LENGTH_SHORT).show()
            }

        }

       getProfes()

    }

    fun add_aula(){
        val au = Aula(
            txt_id_aula.text.toString().trim(),
            txt_nomb_aula.text.toString().trim(),
            txt_prof_selec.text.toString().trim(),
            txt_desc_aula.text.toString().trim()
        )

        val request = ServiceBuilder.buildService(AulasApi::class.java)
        val call = request.addAula(au)

        call.enqueue(object : Callback<ResponseBody> {

            override fun onResponse(
                call: Call<ResponseBody>,
                response: Response<ResponseBody>
            ) {
                if (response.code() == 200) {
                    Toast.makeText(contexto, getString(R.string.registroInserta), Toast.LENGTH_SHORT)
                        .show()

                    finish()
                } else {
                    Toast.makeText(
                        contexto,
                        "Algo ha fallado en la inserción",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Toast.makeText(contexto, getString(R.string.falloConexion), Toast.LENGTH_LONG)
                    .show()
            }
        })
    }

    fun mod_aula(){

        var idA = txt_id_aula.text.toString().trim()
        var nom = txt_nomb_aula.text.toString().trim()
        var prof = txt_prof_selec.text.toString().trim()
        var des = txt_desc_aula.text.toString().trim()

        var au = Aula(
            "${idA}",
            "${nom}",
            "${prof}",
            "${des}"
        )

        val request = ServiceBuilder.buildService(AulasApi::class.java)
        val call = request.modAula(au)
        call.enqueue(object : Callback<ResponseBody> {

            override fun onResponse(
                call: Call<ResponseBody>,
                response: Response<ResponseBody>
            ) {
                //Log.e("Che", response.message())
                if (response.code() == 200) {
                    Toast.makeText(contexto, getString(R.string.registroModi), Toast.LENGTH_SHORT)
                        .show()
                    ActualUser.aulaSeleccionada = Aula()
                    ActualUser.modificandoAula = false
                    finish()
                } else {
                    Toast.makeText(
                        contexto,
                        "Algo ha fallado en la modificación",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Toast.makeText(contexto, getString(R.string.falloConexion), Toast.LENGTH_LONG)
                    .show()
            }
        })
    }

    fun rellenar_campos_aula_mod(){
        var a = ActualUser.aulaSeleccionada as Aula
        txt_id_aula.setText(a.IdAula.toString())
        txt_nomb_aula.setText(a.Nombre.toString())
        txt_desc_aula.setText(a.Descripcion.toString())
        txt_prof_selec.setText(a.NombreProfesor.toString())
    }
    fun check_campos():Boolean{
        if(txt_prof_selec.text.equals("")){
            return false
        }
        if(txt_nomb_aula.text.equals("")){
            return false
        }
        if(txt_id_aula.text.equals("")){
            return false
        }
        if(txt_desc_aula.text.equals("")){
            return false
        }
        return true
    }

    fun getProfes(){

        val request = ServiceBuilder.buildService(PersonaApi::class.java)
        val call = request.getPersonas()

        call.enqueue(object : Callback<MutableList<Persona>> {
            override fun onResponse(call: Call<MutableList<Persona>>, response: Response<MutableList<Persona>>) {

                if (response.isSuccessful){
                    for (post in response.body()!!) {
                        profes.add(Persona(post.DNI,post.Nombre,post.Clave,post.Tfno))
                    }
                    cargarRV()
                }else{
                    //Toast.makeText(contexto, "Error de conexion al traer las personas", Toast.LENGTH_SHORT).show()
                }
            }
            override fun onFailure(call: Call<MutableList<Persona>>, t: Throwable) {
                Toast.makeText(contexto, getString(R.string.falloConexion), Toast.LENGTH_SHORT).show()
            }
        })

    }

    fun cargarRV(){
        miRecyclerView = findViewById(R.id.rv_profesores_new_aula)
        miRecyclerView.setHasFixedSize(true)
        miRecyclerView.layoutManager = LinearLayoutManager(this)
        miAdapterTarea = AdapterProfesores(this, profes,txt_prof_selec)
        miRecyclerView.adapter = miAdapterTarea
    }
}