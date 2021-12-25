package com.example.pruebatecnica

import com.example.pruebatecnica.Respuestas.Pelis
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

interface APIService {
    @GET
   suspend fun getPelisPorNombre(@Url url:String):Response<Pelis>
}