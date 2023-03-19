package com.example.ud7cp1

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class Activity3 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_3)
        val nombre = intent.getStringExtra("nombre")
        val codigoPostal = intent.getStringExtra("codigoPostal")

        val etCodigoPostal= findViewById<EditText>(R.id.etCodigoPostal)
        codigoPostal.also { etCodigoPostal.setText(it) }

        val btnEnviar = findViewById<Button>(R.id.btnEnviar3)
        btnEnviar.setOnClickListener{
            val intent = Intent()
            val codigo_postal = etCodigoPostal.text.toString()
            intent.putExtra("nombre", nombre)
            intent.putExtra("codigoPostal", codigo_postal)
            setResult(RESULT_OK, intent)
            finish()
        }

        val btnVolver = findViewById<Button>(R.id.btnVolver3)
        btnVolver.setOnClickListener{

            finish()
        }
    }
}