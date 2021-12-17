package com.chema.desafio2.Api

import com.chema.desafio2.modelo.Aula
import com.chema.desafio2.modelo.Persona
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface AulasApi {

    @GET("aulas")
    fun getAulas(): Call<MutableList<Aula>>

    @DELETE("borraraula/{id}")
    fun borrarAula(@Path("id") id:String) : Call<ResponseBody>

    @Headers("Content-Type:application/json")
    @POST("addaula")
    fun addAula(@Body info: Aula) : Call<ResponseBody>

    @Headers("Content-Type:application/json")
    @PUT("modificarAula")
    fun modAula(@Body info: Aula) : Call<ResponseBody>
}