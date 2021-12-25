package com.example.pruebatecnica.Respuestas

import com.google.gson.annotations.SerializedName

data class Pelis(@SerializedName("page") var pagina:Int, @SerializedName("results") var resultados:List<Peliculas> ) {





}