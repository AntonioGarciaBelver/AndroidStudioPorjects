package activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.ud7cp2.R
import com.jlara.ejemplosqlite_kotlin_u.ComunidadCRUD

class Activity2 : AppCompatActivity() {

    val comunidadCrud = ComunidadCRUD(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_2)

        val imagen = intent.getIntExtra("imagen",0)
        val etImagen = findViewById<ImageView>(R.id.imageView2)
        imagen.also{ etImagen.setImageResource(imagen)}

        val tvNombre = findViewById<TextView>(R.id.textView2)
        val nombre = intent.getStringExtra("nombre")
        val etNombre = findViewById<EditText>(R.id.etNombre)
        nombre.also { etNombre.setText(it) }
        nombre.also { tvNombre.text = it }

        val btnCambiar = findViewById<Button>(R.id.btnCambiar)
        btnCambiar.setOnClickListener{
            val intent = Intent()
            val name = etNombre!!.text.toString()
            println(name)

            intent.putExtra("nombre", name)
            intent.putExtra("imagen", imagen)

            setResult(RESULT_OK, intent)
            finish()
        }

        val btnCancelar = findViewById<Button>(R.id.btnCancelar)
        btnCancelar.setOnClickListener{

            finish()
        }

    }

}

