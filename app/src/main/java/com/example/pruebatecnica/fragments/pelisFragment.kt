package com.example.pruebatecnica.fragments

import android.os.Bundle
import android.renderscript.ScriptGroup
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pruebatecnica.APIService
import com.example.pruebatecnica.R
import com.example.pruebatecnica.adapters.PelisAdapter
import com.example.pruebatecnica.databinding.FragmentPelisBinding
import com.example.pruebatecnica.utils.Credenciales
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*

class pelisFragment : Fragment(), SearchView.OnQueryTextListener {

    private var _binding: FragmentPelisBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: PelisAdapter
    private var pelisImagen = mutableListOf<String>()
    private var pelisTitulo = mutableListOf<String>()
    private var pelisDesc = mutableListOf<String>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPelisBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.svPelis.setOnQueryTextListener(this)
        buscarPopular("popular")
        initRecyclerView()
    }

   private fun initRecyclerView(){
       adapter = PelisAdapter(pelisImagen, pelisTitulo, pelisDesc)
       binding.rvPelis.layoutManager = LinearLayoutManager(activity)
       binding.rvPelis.adapter = adapter

   }


    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Credenciales.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    }

    private fun buscarPorNombre(query:String){
        CoroutineScope(Dispatchers.IO).launch {
            val call = getRetrofit().create(APIService::class.java).getPelisPorNombre("search/movie?api_key=" + Credenciales.KEY + "&query=$query")
            val peliculas = call.body()

            getActivity()?.runOnUiThread(){
                if (call.isSuccessful){
                    val listapelis = peliculas?.resultados ?: Collections.emptyList()
                    val listaURL = mutableListOf<String>()
                    val listaTitulos = mutableListOf<String>()
                    val listaDesc = mutableListOf<String>()

                    for(i in 0 until listapelis.size-1 ){
                        listaURL.add(Credenciales.URL_IMAGEN + listapelis[i].imagen)
                        listaTitulos.add(listapelis[i].nombre)
                        listaDesc.add(listapelis[i].desc)
                    }
                    pelisImagen.clear()
                    pelisImagen.addAll(listaURL)
                    pelisTitulo.clear()
                    pelisTitulo.addAll(listaTitulos)
                    pelisDesc.clear()
                    pelisDesc.addAll(listaDesc)
                    adapter.notifyDataSetChanged()
                }else {
                    showError()
                }
            }
        }
    }


    private fun buscarPopular(query:String){
        CoroutineScope(Dispatchers.IO).launch {
            val call = getRetrofit().create(APIService::class.java).getPelisPorNombre("movie/$query?api_key=" + Credenciales.KEY)
            val peliculas = call.body()
            getActivity()?.runOnUiThread(){
                if (call.isSuccessful){
                    val listapelis = peliculas?.resultados ?: Collections.emptyList()
                    val listaURL = mutableListOf<String>()
                    val listaTitulos = mutableListOf<String>()
                    val listaDesc = mutableListOf<String>()
                    for(i in 0 until listapelis.size-1 ){
                        listaURL.add(Credenciales.URL_IMAGEN + listapelis[i].imagen)
                        listaTitulos.add(listapelis[i].nombre)
                        listaDesc.add(listapelis[i].desc)
                    }
                    pelisImagen.clear()
                    pelisImagen.addAll(listaURL)
                    pelisTitulo.clear()
                    pelisTitulo.addAll(listaTitulos)
                    pelisDesc.clear()
                    pelisDesc.addAll(listaDesc)
                    adapter.notifyDataSetChanged()
                    System.out.println("pelicula cargada")
                }else {
                    showError()
                }
            }
        }
    }


    private fun showError(){
        Toast.makeText(activity, "Error al cargar peliculas", Toast.LENGTH_SHORT).show()
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        if (!query.isNullOrEmpty()){
            buscarPorNombre(query.toLowerCase())
        }
        return true
    }


    override fun onQueryTextChange(newText: String?): Boolean {
        return true
    }

}