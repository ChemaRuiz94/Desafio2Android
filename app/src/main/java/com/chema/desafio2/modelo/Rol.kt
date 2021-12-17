package com.chema.desafio2.modelo
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class Rol (@SerializedName("id")
           val id: String? = null,
           @SerializedName("descripcion")
           val descripcion: String? = null,
) : Serializable {
    override fun toString(): String {
        return descripcion.toString()!!
    }
}