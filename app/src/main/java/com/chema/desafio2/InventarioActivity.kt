package com.chema.desafio2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.chema.desafio2.Api.AulasApi
import com.chema.desafio2.Api.PersonaApi
import com.chema.desafio2.Api.ServiceBuilder
import com.chema.desafio2.fragments.MyFragmentProfesores
import com.chema.desafio2.fragments.MyFragmentsAulas
import com.chema.desafio2.modelo.ActualUser
import com.chema.desafio2.modelo.Aula
import com.chema.desafio2.modelo.Persona
import com.chema.desafio2.modelo.Rol
import com.google.android.material.floatingactionbutton.FloatingActionButton
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class InventarioActivity : AppCompatActivity() {

    private lateinit var usuario: Persona
    lateinit var txt_nom : TextView
    lateinit var txt_rol : TextView

    lateinit var btn_aulas: Button
    lateinit var btn_prof: Button
    lateinit var btn_new_prof: Button
    lateinit var btn_new_aula: Button
    lateinit var flt_btn_preferences: FloatingActionButton

    var aulas: ArrayList<Aula> = ArrayList<Aula>()
    var contexto = this

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inventario)

        txt_nom = findViewById(R.id.txt_nombre_inventario)
        txt_rol = findViewById(R.id.txt_rol_inventario)

        btn_aulas = findViewById(R.id.btn_gestion_aulas)
        btn_prof = findViewById(R.id.btn_gestion_profesores)
        btn_new_prof = findViewById(R.id.btn_add_profesor)
        btn_new_aula = findViewById(R.id.btn_add_aula)
        flt_btn_preferences = findViewById(R.id.floatingActionButton_preferencias_inventario)

        var act_user: Persona = ActualUser.actualUser

        usuario = act_user
        if(usuario != null){
            txt_nom.setText(usuario!!.Nombre)
            check_rol()
        }

        btn_new_aula.setOnClickListener{
            val intent = Intent(contexto,NewAulaActivity::class.java)
            ActualUser.modificandoAula = false
            startActivity(intent)
        }

        btn_aulas.setOnClickListener{
            val fragment1 = MyFragmentsAulas()
            replaceFragmentAulas(fragment1)
        }

        btn_prof.setOnClickListener{
            val fragment2 = MyFragmentProfesores()
            replaceFragmentProfesores(fragment2)
        }

        flt_btn_preferences.setOnClickListener{
            preferences()
        }

        btn_new_prof.setOnClickListener{
            val intent = Intent(contexto,SignUpActivity::class.java)
            ActualUser.actualUserModif = Persona()
            ActualUser.modificando = false
            startActivity(intent)
        }

    }


    fun replaceFragmentAulas(fragment: MyFragmentsAulas){
        val fragmentTransaction =supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragment_inventario, fragment)

        fragmentTransaction.commit()
    }

    fun replaceFragmentProfesores(fragment: MyFragmentProfesores){
        val fragmentTransaction =supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragment_inventario, fragment)

        fragmentTransaction.commit()
    }


    fun preferences(){

        AlertDialog.Builder(this).setTitle("PREFERENCIAS")
            .setPositiveButton("Modificar perfil de Usuario") { view, _ ->

                //MODIFICAR
                val intent = Intent(this, SignUpActivity::class.java)
                ActualUser.modificando = true
                ActualUser.actualUserModif = ActualUser.actualUser
                startActivity(intent)
                view.dismiss()
            }

            .setNeutralButton("Cerrar sesion") { view, _ ->

                //CERRAR SESION
                ActualUser.actualUser = Persona()
                var myIntent = Intent(contexto,LoginActivity::class.java)
                startActivity(myIntent)


                view.dismiss()}

            .setNegativeButton("Eliminar perfil de Usuario") { view, _ ->

                //ELIMINAR

                //CERRAR SESION
                check_delete()
                view.dismiss()

            }
            .create().show()
    }

    fun check_delete(){
        AlertDialog.Builder(this).setTitle(getString(R.string.eliminarperfil))
            .setPositiveButton(getString(R.string.aceptar)) { view, _ ->

                //ELIMINAR
                delete_user(usuario.Nombre!!)
                ActualUser.actualUser = Persona()
                ActualUser.modificando = false
                var myIntent = Intent(contexto,LoginActivity::class.java)
                startActivity(myIntent)

                view.dismiss()
            }

            .setNegativeButton(getString(R.string.cancelar)) { view, _ ->

                //CANCELAR
                view.dismiss()

            }
            .create().show()
    }
    fun delete_user(nombreUser : String){
        val request = ServiceBuilder.buildService(PersonaApi::class.java)
        val call = request.borrarUsuario(nombreUser)
        call.enqueue(object : Callback<ResponseBody> {

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {

                if (response.code() == 200) {
                    Toast.makeText(contexto, "Registro eliminado con éxito",Toast.LENGTH_SHORT).show()
                }
                else {
                    Toast.makeText(contexto, "Algo ha fallado en el borrado: Nombre de usuario no encontrado",Toast.LENGTH_LONG).show()
                }
            }
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Toast.makeText(contexto, "Algo ha fallado en la conexión.",Toast.LENGTH_LONG).show()
            }
        })
    }
    fun check_rol(){

        val us = ActualUser.actualUser
        val request = ServiceBuilder.buildService(PersonaApi::class.java)
        Log.e("Chema DANI", us.DNI.toString())
        val call = request.rolUsuario(us)


        call.enqueue(object : Callback<MutableList<Rol>> {

            override fun onResponse(
                call: Call<MutableList<Rol>>,
                response: Response<MutableList<Rol>>
            ) {

                if (response.isSuccessful) {
                    var msg = ""
                    for (post in response.body()!!) {
                        msg +=post.descripcion.toString()+"; "

                    }
                    //pone los roles
                    txt_rol.setText(msg)
                } else {
                    Toast.makeText(contexto, "FALLO AL TRAER LOS ROLES", Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<MutableList<Rol>>, t: Throwable) {
                Toast.makeText(contexto, "Algo ha fallado en la conexión.", Toast.LENGTH_LONG).show()
            }
        })
    }

    override fun onBackPressed() {
        AlertDialog.Builder(this)
            .setTitle("SALIR Y CERRAR SESION?")
            .setPositiveButton(getString(R.string.aceptar)) { view, _ ->
                ActualUser.actualUser = Persona()
                ActualUser.modificando = false
                super.onBackPressed()
                view.dismiss()
            }
            .setNegativeButton(getString(R.string.cancelar)) { view, _ ->
                //super.onBackPressed()
                view.dismiss()
            }
            .setCancelable(true)
            .create()
            .show()
    }
}
