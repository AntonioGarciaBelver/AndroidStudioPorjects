package com.example.ud7cp1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var intentLaunch: ActivityResultLauncher<Intent>
    private var nombre: String? = "Nombre"
    private var codigoPostal: String? = "Código Postal"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnCambiarNombre = findViewById<Button>(R.id.btnCambiarNombre)
        btnCambiarNombre.setOnClickListener(this)

        val btnCambiarCP = findViewById<Button>(R.id.btnCp)
        btnCambiarCP.setOnClickListener{
            val intent = Intent(this, Activity3::class.java)
            intent.putExtra("nombre", nombre)
            intent.putExtra("codigoPostal", codigoPostal)
            intentLaunch.launch(intent)
        }

        val tvTexto = findViewById<TextView>(R.id.tvTexto)
        tvTexto.text = "Nombre"

        val tvTexto2 = findViewById<TextView>(R.id.tvTexto2)
        tvTexto2.text = "Código Postal"

        intentLaunch = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result: ActivityResult ->
            if (result.resultCode == RESULT_OK) {
                nombre = result.data?.extras?.getString("nombre")
                codigoPostal = result.data?.extras?.getString("codigoPostal")
                tvTexto.text = nombre
                tvTexto2.text = codigoPostal
            }
        }
    }

    override fun onClick(p0: View?) {
        val intent = Intent(this, Activity2::class.java)
        intent.putExtra("nombre", nombre)
        intent.putExtra("codigoPostal", codigoPostal)
        intentLaunch.launch(intent)
    }
}
