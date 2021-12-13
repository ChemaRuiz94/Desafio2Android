package com.chema.desafio2.modelo

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Persona(@SerializedName("DNI")
                   val DNI: String? = null,

                   @SerializedName("Nombre")
                   val Nombre: String? = null,

                   @SerializedName("Clave")
                   val Clave: String? = null,

                   @SerializedName("Tfno")
                   val Tfno: String? = null) :Serializable
