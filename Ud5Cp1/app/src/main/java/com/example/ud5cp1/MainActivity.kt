package com.example.ud5cp1

import android.app.DatePickerDialog
import android.app.Dialog
import android.app.TimePickerDialog
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.View.OnClickListener
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import java.time.LocalDate
import java.util.*

class MainActivity : AppCompatActivity(), OnClickListener {

    var c = Calendar.getInstance()
    var mYearActual = c[Calendar.YEAR]
    var mMonthActual = c[Calendar.MONTH]
    var mDayActual = c[Calendar.DAY_OF_MONTH]

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnDialog = findViewById<Button>(R.id.btnDialog)
        btnDialog.setOnClickListener(this)

        var txtDate = findViewById<TextView>(R.id.tvDate)

        txtDate.text =
            ("La fecha actual es " + mDayActual + "/" + (mMonthActual + 1) + "/" + mYearActual)

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val toast: Toast
        when (item.itemId) {
            R.id.archivo -> {
                toast = Toast.makeText(
                    applicationContext,
                    "Abrir archivo", Toast.LENGTH_SHORT
                )
                toast.show()
            }
            R.id.crear -> {
                toast = Toast.makeText(
                    applicationContext,
                    "Abrir crear", Toast.LENGTH_SHORT
                )
                toast.show()
            }
            R.id.abrir -> {
                toast = Toast.makeText(
                    applicationContext,
                    "Abrir abrir", Toast.LENGTH_SHORT
                )
                toast.show()
            }

        }

        return true
    }

    override fun onClick(p0: View?) {
        if (p0 != null) {
            when (p0.id) {
                R.id.btnDialog -> {
                    val txtDate = findViewById<TextView>(R.id.textView)
                    val c = Calendar.getInstance()
                    val mYear = c[Calendar.YEAR]
                    val mMonth = (c[Calendar.MONTH] + 1)
                    val mDay = c[Calendar.DAY_OF_MONTH]
                    var calculo = IntArray(2)
                    var txtTime = findViewById<TextView>(R.id.tvTime)
                    val datePickerDialog = DatePickerDialog(this,
                        { datePicker, i, i1, i2 ->
                            "Naciste el $i2/${i1 + 1}/$i".also { txtDate.text = it }.also {
                                calculo =
                                    calculaDias(mMonthActual, i1, mDayActual, i2, mYearActual, i)
                            }.also {
                                txtTime.text =
                                    "Tienes " + calculo[0] + " años y " + calculo[1] + " días."
                            }
                        }, mYear, mMonth, mDay
                    )
                    datePickerDialog.show()
                }
            }
        }
    }

    private fun calculaDias(
        mesActual: Int,
        mesNacimiento: Int,
        diaActual: Int,
        diaNacimiento: Int,
        anhoActual: Int,
        anhoNacimiento: Int
    ): IntArray {
        var anhos = 0
        var dias = 0
        val calculo = IntArray(2)
        anhos = anhoActual - anhoNacimiento
        if (mesActual >= mesNacimiento) {
            for (i in mesNacimiento + 1..mesActual) dias += numeroDeDias(i, anhoActual)
            if (diaActual < diaNacimiento && mesActual == mesNacimiento) {
                anhos--
                dias = 365 + diaActual - diaNacimiento
            } else {
                dias += diaActual - diaNacimiento
            }
        } else {
            anhos--
            dias = diasRestantes(diaNacimiento, mesNacimiento, anhoActual - 1)
            val veces = 12 + mesActual - mesNacimiento - 1
            var mesVer = mesNacimiento + 1
            var anhoVer = anhoActual - 1
            for (i in 1..veces) {
                if (mesVer > 12) {
                    mesVer = 1
                    anhoVer++
                }
                dias += numeroDeDias(mesVer, anhoVer)
                mesVer++
            }
            dias += diaActual
        }
        calculo[0] = anhos
        calculo[1] = dias
        return calculo
    }

    private fun esBisiesto(anho: Int): Boolean {
        var bisiesto = false
        if (anho > 1582) {
            if (anho % 400 == 0 || anho % 4 == 0 && anho % 100 != 0) {
                bisiesto = true
            }
        } else {
            bisiesto = false
        }
        return bisiesto
    }

    private fun numeroDeDias(m: Int, a: Int): Int {
        return when (m) {
            1, 3, 5, 7, 8, 10, 12 -> 31
            4, 6, 9, 11 -> 30
            else -> if (esBisiesto(a)) {
                29
            } else {
                28
            }
        }
    }

    private fun diasRestantes(dia: Int, mes: Int, anho: Int): Int {
        return numeroDeDias(mes, anho) - dia
    }

}