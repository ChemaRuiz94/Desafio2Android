package com.chema.desafio2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.chema.desafio2.Api.PersonaApi
import com.chema.desafio2.Api.ServiceBuilder
import com.chema.desafio2.modelo.ActualUser
import com.chema.desafio2.modelo.Persona
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignUpActivity : AppCompatActivity() {
    private  lateinit var  txt_user_name : EditText
    private  lateinit var  txt_pwd : EditText
    private  lateinit var  txt_dni : EditText
    private  lateinit var  txt_tlf : EditText

    private  lateinit var btn_acept : Button

    //private var modificando: Boolean = false
    private lateinit var mod: String
    private lateinit var user: Persona
    var contexto = this

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        txt_user_name = findViewById(R.id.ed_txt_userName_signUp)
        txt_pwd = findViewById(R.id.ed_txt_pwd_signUp)
        txt_dni = findViewById(R.id.ed_txt_dni_signUp)
        txt_tlf = findViewById(R.id.ed_txt_telefono_signUp)

        btn_acept = findViewById(R.id.btn_confirmar_signUp)


        if(ActualUser.modificando == true) {

            user = ActualUser.actualUserModif
            btn_acept.setText(getString(R.string.modificar))
            cargar_campos_mod()
        }


        btn_acept.setOnClickListener{
            if(check_campos_vacios()){
                if(ActualUser.modificando == true){
                    //MODIFICAR
                    modificarUsuario()

                }else{
                    //aqui es donde deberia ir el check_usuario_existe()
                    registarNewUsuario()
                }
            }else{
                Toast.makeText(this,"Rellene todos los campos",Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun cargar_campos_mod(){
        txt_user_name.setText(user.Nombre)
        txt_pwd.setText(user.Clave)
        txt_dni.setText(user.DNI)
        txt_tlf.setText(user.Tfno)
    }

    fun modificarUsuario(){

        var dni = txt_dni.text.toString().trim()
        var nom = txt_user_name.text.toString().trim()
        var pwd = txt_pwd.text.toString().trim()
        var tlf = txt_tlf.text.toString().trim()

        var us = Persona(
            "${dni}",
           "${nom}",
            "${pwd}",
            "${tlf}"
        )

        val request = ServiceBuilder.buildService(PersonaApi::class.java)
        val call = request.modUsuario(us)
        call.enqueue(object : Callback<ResponseBody> {

            override fun onResponse(
                call: Call<ResponseBody>,
                response: Response<ResponseBody>
            ) {
                //Log.e("Che", response.message())
                if (response.code() == 200) {
                    Toast.makeText(contexto, "Registro modificado con éxito", Toast.LENGTH_SHORT)
                        .show()
                    ActualUser.actualUser = us
                    ActualUser.modificando = false
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
                Toast.makeText(contexto, "Algo ha fallado en la conexión.", Toast.LENGTH_LONG)
                    .show()
            }
        })

    }

    fun check_campos_vacios(): Boolean{
        if(txt_dni.text.toString().trim().equals("")){
            return false
        }
        if(txt_user_name.text.toString().trim().equals("")){
            return false
        }
        if(txt_pwd.text.toString().trim().equals("")){
            return false
        }
        if(txt_tlf.text.toString().trim().equals("")){
            return false
        }
        return true
    }
    fun registarNewUsuario(){
        val us = Persona(
            txt_dni.text.toString().trim(),
            txt_user_name.text.toString().trim(),
            txt_pwd.text.toString().trim(),
            txt_tlf.text.toString().trim()
        )

        val request = ServiceBuilder.buildService(PersonaApi::class.java)
        val call = request.addUsuario(us)

        call.enqueue(object : Callback<ResponseBody> {

            override fun onResponse(
                call: Call<ResponseBody>,
                response: Response<ResponseBody>
            ) {
                if (response.code() == 200) {
                    add_rol_new_user()
                    Toast.makeText(contexto, "Registro efectuado con éxito", Toast.LENGTH_SHORT)
                        .show()
                    ActualUser.modificando = false
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
                Toast.makeText(contexto, "Algo ha fallado en la conexión.", Toast.LENGTH_LONG)
                    .show()
            }
        })
    }

    fun add_rol_new_user(){
        val us = Persona(
            txt_dni.text.toString().trim(),
            txt_user_name.text.toString().trim(),
            txt_pwd.text.toString().trim(),
            txt_tlf.text.toString().trim()
        )

        val request = ServiceBuilder.buildService(PersonaApi::class.java)
        val call = request.addNewRol(us)
        Log.e("Chema",us.Nombre.toString())
        call.enqueue(object : Callback<ResponseBody> {

            override fun onResponse(
                call: Call<ResponseBody>,
                response: Response<ResponseBody>
            ) {
                if (response.code() == 200) {
                    //Toast.makeText(contexto, "Registro efectuado con éxito", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(
                        contexto,
                        "Algo ha fallado en la inserción del rol",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Toast.makeText(contexto, "Algo ha fallado en la conexión de inserccion del rol.", Toast.LENGTH_LONG)
                    .show()
            }
        })
    }
}