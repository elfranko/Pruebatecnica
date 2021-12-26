package com.example.pruebatecnica.fragments

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.FragmentContainer
import com.example.pruebatecnica.R
import com.example.pruebatecnica.databinding.FragmentPelisBinding
import com.example.pruebatecnica.databinding.FragmentSubirfotoBinding
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference


class subirfotoFragment : Fragment() {

    private var _binding: FragmentSubirfotoBinding? = null
    private val binding get() = _binding!!


    private val File = 1
    private val database = Firebase.database
    val myRef = database.getReference("user")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSubirfotoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
       binding.button.setOnClickListener {
           subirFila()
       }

    }

    fun subirFila(){
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "*/*"
        startActivityForResult(intent,File)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == File){
            if (resultCode == RESULT_OK){
                val FileUri = data!!.data
                val Folder: StorageReference =
                    FirebaseStorage.getInstance().getReference().child("User")
                val file_name : StorageReference = Folder.child("file" + FileUri!!.lastPathSegment)
                file_name.putFile(FileUri).addOnSuccessListener { taskSnapshot ->
                    file_name.downloadUrl.addOnSuccessListener { uri ->
                        val hashMap =
                           HashMap<String, String>()
                        hashMap["link"] = java.lang.String.valueOf(uri)
                        myRef.setValue(hashMap)
                        Toast.makeText(activity, "Se cargo la imagen", Toast.LENGTH_SHORT)
                    }

                }
            }
        }
    }

}