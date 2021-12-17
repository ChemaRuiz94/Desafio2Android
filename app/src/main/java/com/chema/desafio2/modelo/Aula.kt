package com.chema.desafio2.modelo

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Aula(@SerializedName("IdAula")
                val IdAula: String? = null,

                @SerializedName("Nombre")
                val Nombre: String? = null,

                @SerializedName("NombreProfesor")
                val NombreProfesor: String? = null,

                @SerializedName("Descripcion")
                val Descripcion: String? = null) : Serializable {}