package com.mylabsystems.appimc.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.mylabsystems.appimc.R
import com.mylabsystems.appimc.dao.IMCDAO

class MainActivity : AppCompatActivity(R.layout.activity_main) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val edtPeso = findViewById<EditText>(R.id.edt_peso)
        val edtAltura = findViewById<EditText>(R.id.edt_altura)
        val btnCalcular = findViewById<Button>(R.id.btn_calcular)

        btnCalcular.setOnClickListener {
            val peso = edtPeso.text.toString().replace(",", ".").toDoubleOrNull()
            val altura = edtAltura.text.toString().replace(",", ".").toDoubleOrNull()

            if (peso == null || altura == null){
                Toast.makeText(this, "Preencha os dados corretamente!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val retorno = IMCDAO.calcular(peso, altura)
            Log.i("ESTADO",retorno)
            Toast.makeText(this, retorno, Toast.LENGTH_SHORT).show()

            edtPeso.text.clear()
            edtAltura.text.clear()

            val intent = Intent(this, ResultadoActivity::class.java)
            startActivity(intent)
        }
    }
}