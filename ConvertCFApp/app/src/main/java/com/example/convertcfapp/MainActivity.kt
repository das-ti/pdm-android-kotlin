package com.example.convertcfapp

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val edtCelsius = findViewById<EditText>(R.id.edt_celsius)
        val btnConverter = findViewById<Button>(R.id.btn_converter)
        val txvResultado = findViewById<TextView>(R.id.txv_resultado)

        btnConverter.setOnClickListener {
            val celsiusTexto = edtCelsius.text.toString()

            if (validarCampo(celsiusTexto)) {
                val celsius = celsiusTexto.toDouble()
                val fahrenheit = celsius * 9 / 5 + 32

                txvResultado.text = getString(
                    R.string.resultado_fahrenheit,
                    celsius.toString(),
                    fahrenheit.toString()
                )
            }
        }
    }

    private fun validarCampo(valor: String): Boolean {
        if (valor.isBlank()) {
            Toast.makeText(this, getString(R.string.erro_campo_vazio), Toast.LENGTH_SHORT).show()
            return false
        }

        if (valor.toDoubleOrNull() == null) {
            Toast.makeText(this, getString(R.string.erro_valor_invalido), Toast.LENGTH_SHORT).show()
            return false
        }

        return true
    }
}