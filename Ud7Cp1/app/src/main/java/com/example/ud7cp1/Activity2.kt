package com.example.ud7cp1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import org.w3c.dom.Text

class Activity2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_2)
        val nombre = intent.getStringExtra("nombre")
        val cp = intent.getStringExtra("codigoPostal")

        val etNombre = findViewById<EditText>(R.id.etNombre)
        nombre.also { etNombre.setText(it) }

        val btnEnviar = findViewById<Button>(R.id.btnEnviar2)
        btnEnviar.setOnClickListener{
            val intent = Intent()
            val name = etNombre.text.toString()
            intent.putExtra("nombre", name)
            intent.putExtra("codigoPostal", cp)
            setResult(RESULT_OK, intent)
            finish()
        }

        val btnVolver = findViewById<Button>(R.id.btnVolver2)
        btnVolver.setOnClickListener{

            finish()
        }
    }
}