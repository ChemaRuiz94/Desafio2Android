package com.chema.desafio2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.chema.desafio2.modelo.ActualUser
import com.chema.desafio2.modelo.Persona

class InventarioActivity : AppCompatActivity() {

    private lateinit var usuario: Persona
    lateinit var txt_nom : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inventario)

        txt_nom = findViewById(R.id.txt_nombre_inventario)

        val user: Persona = ActualUser.actualUser

        if(user != null){
            txt_nom.setText(user!!.Nombre)
        }
    }
}