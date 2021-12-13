package com.chema.desafio2.Api

import com.chema.desafio2.modelo.Persona
import retrofit2.Call
import retrofit2.http.*

interface PersonaApi {

    @GET("personas")
    fun getPersonas(): Call<MutableList<Persona>>

    @GET("/personas/{nombre}")
    fun getPersonaByNombre(@Path("nombre") nombre:String): Call<Persona>

}