package com.chema.desafio2.Api

import com.chema.desafio2.modelo.Persona
import com.chema.desafio2.modelo.Rol
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface PersonaApi {

    @GET("personas")
    fun getPersonas(): Call<MutableList<Persona>>

    @GET("/personas/{nombre}")
    fun getPersonaByNombre(@Path("nombre") nombre:String): Call<Persona>

    @Headers("Content-Type:application/json")
    @POST("registrar")
    fun addUsuario(@Body info: Persona) : Call<ResponseBody>

    @Headers("Content-Type:application/json")
    @POST("login")
    fun loginUsuario(@Body info: Persona) : Call<Persona>
}