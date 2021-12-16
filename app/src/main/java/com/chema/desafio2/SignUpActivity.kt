package com.chema.desafio2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.chema.desafio2.Api.PersonaApi
import com.chema.desafio2.Api.ServiceBuilder
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

    var contexto = this

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        txt_user_name = findViewById(R.id.ed_txt_userName_signUp)
        txt_pwd = findViewById(R.id.ed_txt_pwd_signUp)
        txt_dni = findViewById(R.id.ed_txt_dni_signUp)
        txt_tlf = findViewById(R.id.ed_txt_telefono_signUp)

        btn_acept = findViewById(R.id.btn_confirmar_signUp)

        btn_acept.setOnClickListener{
            if(check_campos_vacios()){
                //aqui es donde deberia ir el check_usuario_existe()
                registarNewUsuario()
            }
        }
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
                    Toast.makeText(contexto, "Registro efectuado con éxito", Toast.LENGTH_SHORT)
                        .show()
                } else {
                    Toast.makeText(
                        contexto,
                        "Algo ha fallado en la inserción",
                        Toast.LENGTH_LONG
                    ).show()
                }
                /*
                if (response.isSuccessful) { //Esto es otra forma de hacerlo en lugar de mirar el código.
                    Log.e("Fernando", "Registro efectuado con éxito.")
                    Toast.makeText(contexto, "Registro efectuado con éxito", Toast.LENGTH_SHORT)
                        .show()
                }

                 */
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Toast.makeText(contexto, "Algo ha fallado en la conexión.", Toast.LENGTH_LONG)
                    .show()
            }
        })
    }
}