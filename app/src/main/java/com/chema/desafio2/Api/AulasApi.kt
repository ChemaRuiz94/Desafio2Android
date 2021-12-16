package com.chema.desafio2.Api

import com.chema.desafio2.modelo.Aula
import com.chema.desafio2.modelo.Persona
import retrofit2.Call
import retrofit2.http.GET

interface AulasApi {

    @GET("aulas")
    fun getAulas(): Call<MutableList<Aula>>
}