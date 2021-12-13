package com.chema.desafio2.Api

import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ServiceBuilder {

    val client = OkHttpClient.Builder().build()
    const val URL = "http://192.168.0.156:5000/"
}