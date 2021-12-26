package com.example.pruebatecnica

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pruebatecnica.Respuestas.Peliculas
import com.example.pruebatecnica.adapters.PelisAdapter
import com.example.pruebatecnica.adapters.VPAdapter
import com.example.pruebatecnica.databinding.ActivityMainBinding
import com.example.pruebatecnica.fragments.pelisFragment
import com.example.pruebatecnica.fragments.subirfotoFragment
import com.example.pruebatecnica.fragments.ubicacionFragment
import com.example.pruebatecnica.utils.Credenciales
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.Dispatcher
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import java.time.temporal.TemporalQuery
import java.util.Collections.emptyList


class MainActivity : AppCompatActivity(){

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: PelisAdapter
    private var pelisImagen = mutableListOf<String>()





    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //binding.svPelis.setOnQueryTextListener(this)
        //buscarPopular("popular")

        //initRecyclerView()


        setUpTabs()

        ///Para quitar la barrita xD
        supportActionBar?.hide()
    }

    private fun setUpTabs(){
        val adapter = VPAdapter(supportFragmentManager)
        adapter.addFragment(pelisFragment(), "Peliculas")
        adapter.addFragment(ubicacionFragment(), "Ubicacion")
        adapter.addFragment(subirfotoFragment(), "Subir foto")
        binding.viewPager.adapter = adapter
        binding.tabBar.setupWithViewPager(binding.viewPager)

        binding.tabBar.getTabAt(0)
        binding.tabBar.getTabAt(1)
        binding.tabBar.getTabAt(2)

    }




 /*  private fun initRecyclerView(){
       adapter = PelisAdapter(pelisImagen)
       binding.rvPelis.layoutManager = LinearLayoutManager(this)
       binding.rvPelis.adapter = adapter

   }*/

    /*private fun getRetrofit():Retrofit{
        return Retrofit.Builder()
            .baseUrl(Credenciales.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    }*/

   /* private fun buscarPorNombre(query:String){
        CoroutineScope(Dispatchers.IO).launch {
            val call = getRetrofit().create(APIService::class.java).getPelisPorNombre("search/movie?api_key=" + Credenciales.KEY + "&query=$query")
            val peliculas = call.body()
            runOnUiThread(){
                if (call.isSuccessful){
                   val listapelis = peliculas?.resultados ?: emptyList()
                    val listaURL = mutableListOf<String>()

                    for(i in 0 until listapelis.size-1 ){
                      listaURL.add(Credenciales.URL_IMAGEN + listapelis[i].imagen)
                    }
                    pelisImagen.clear()
                    pelisImagen.addAll(listaURL)
                    adapter.notifyDataSetChanged()
                }else {
                 showError()
                }
            }
        }
    }*/


    /*private fun buscarPopular(query:String){
        CoroutineScope(Dispatchers.IO).launch {
            val call = getRetrofit().create(APIService::class.java).getPelisPorNombre("movie/$query?api_key=" + Credenciales.KEY)
            val peliculas = call.body()
            runOnUiThread(){
                if (call.isSuccessful){
                    val listapelis = peliculas?.resultados ?: emptyList()
                    val listaURL = mutableListOf<String>()

                    for(i in 0 until listapelis.size-1 ){
                        listaURL.add(Credenciales.URL_IMAGEN + listapelis[i].imagen)
                    }
                    pelisImagen.clear()
                    pelisImagen.addAll(listaURL)
                    adapter.notifyDataSetChanged()
                }else {
                    showError()
                }
            }
        }
    }*/


/*private fun showError(){
    Toast.makeText(this, "Error al cargar peliculas", Toast.LENGTH_SHORT).show()
}*/

   /* override fun onQueryTextSubmit(query: String?): Boolean {
        if (!query.isNullOrEmpty()){
            buscarPorNombre(query.toLowerCase())
        }
    return true
    }


    override fun onQueryTextChange(newText: String?): Boolean {
      return true
    }*/

}