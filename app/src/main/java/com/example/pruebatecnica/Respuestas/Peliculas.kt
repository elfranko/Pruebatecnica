package com.example.pruebatecnica.Respuestas

import com.google.gson.annotations.SerializedName

data class Peliculas(@SerializedName("id") var id:Int, @SerializedName("title") var nombre:String, @SerializedName("poster_path") var imagen:String,
                    @SerializedName("overview") var desc:String)

