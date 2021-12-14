package com.chema.desafio2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.chema.desafio2.Api.PersonaApi
import com.chema.desafio2.Api.ServiceBuilder
import com.chema.desafio2.modelo.Persona
import com.chema.desafio2.modelo.Rol
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {

    private  lateinit var  txt_user_name : EditText
    private  lateinit var  txt_pwd : EditText

    private  lateinit var btn_signUp : Button
    private  lateinit var btn_login : Button

    var contexto = this


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        txt_user_name = findViewById(R.id.ed_txt_usuario_login)
        txt_pwd = findViewById(R.id.ed_txt_pwd_login)

        btn_login = findViewById(R.id.btn_login)
        btn_signUp = findViewById(R.id.btn_signup)

        btn_signUp.setOnClickListener{
            intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }

        btn_login.setOnClickListener{
            if(check_campos_vacios()){
                check_login()
            }
        }
    }

    fun check_campos_vacios(): Boolean{
        if(txt_user_name.text.toString().trim().equals("")){
            return false
        }
        if(txt_pwd.text.toString().trim().equals("")){
            return false
        }
        return true
    }

    fun check_login(){

        val nom = txt_user_name.text.toString().trim()
        val pwd = txt_pwd.text.toString().trim()

        val us = Persona(
            "",
            "${nom}",
            "${pwd}",
            ""
        )
        val request = ServiceBuilder.buildService(PersonaApi::class.java)
        val call = request.loginUsuario(us)

        var intentV1 = Intent(this, MainActivity::class.java)


        call.enqueue(object : Callback<MutableList<Persona>> {

            override fun onResponse(
                call: Call<MutableList<Persona>>,
                response: Response<MutableList<Persona>>
            ) {

                if (response.code() == 200) {
                    var per = response.body()
                    var nombre = per!![0].Nombre

                    startActivity(intentV1)
                    //Toast.makeText(contexto, "BIEEEN ${nombre}", Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(contexto, "MAAAL", Toast.LENGTH_LONG).show()
                    //Toast.makeText(contexto, response.message().toString(), Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<MutableList<Persona>>, t: Throwable) {
                Toast.makeText(contexto, "Algo ha fallado en la conexión.", Toast.LENGTH_LONG).show()
            }
        })
    }

}