package com.example.leideohmapp

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

        val edtTensao = findViewById<EditText>(R.id.edt_tensao)
        val edtResistencia = findViewById<EditText>(R.id.edt_resistencia)
        val edtCorrente = findViewById<EditText>(R.id.edt_corrente)
        val btnCalcular = findViewById<Button>(R.id.btn_calcular)
        val txvResultado = findViewById<TextView>(R.id.txv_resultado)

        btnCalcular.setOnClickListener {
            val tensaoTexto = edtTensao.text.toString()
            val resistenciaTexto = edtResistencia.text.toString()
            val correnteTexto = edtCorrente.text.toString()

            val preenchidos = listOf(tensaoTexto, resistenciaTexto, correnteTexto)
                .count { it.isNotBlank() }

            if (preenchidos < 2) {
                Toast.makeText(this, getString(R.string.erro_poucos_campos), Toast.LENGTH_SHORT).show()
            } else if (preenchidos == 3) {
                Toast.makeText(this, getString(R.string.erro_todos_campos), Toast.LENGTH_SHORT).show()
            } else if (tensaoTexto.isNotBlank() && resistenciaTexto.isNotBlank() && correnteTexto.isBlank()) {
                val tensao = tensaoTexto.toDoubleOrNull()
                val resistencia = resistenciaTexto.toDoubleOrNull()

                if (tensao == null || resistencia == null) {
                    Toast.makeText(this, getString(R.string.erro_valor_invalido_ohm), Toast.LENGTH_SHORT).show()
                } else if (resistencia == 0.0) {
                    Toast.makeText(this, getString(R.string.erro_divisao_zero), Toast.LENGTH_SHORT).show()
                } else {
                    val resultado = tensao / resistencia
                    val corrente = String.format("%.2f", resultado)
                    txvResultado.text = getString(R.string.resultado_corrente, corrente.toString())
                }
            } else if (tensaoTexto.isNotBlank() && correnteTexto.isNotBlank() && resistenciaTexto.isBlank()) {
                val tensao = tensaoTexto.toDoubleOrNull()
                val corrente = correnteTexto.toDoubleOrNull()

                if (tensao == null || corrente == null) {
                    Toast.makeText(this, getString(R.string.erro_valor_invalido_ohm), Toast.LENGTH_SHORT).show()
                } else if (corrente == 0.0) {
                    Toast.makeText(this, getString(R.string.erro_divisao_zero), Toast.LENGTH_SHORT).show()
                } else {
                    val resultado = tensao / corrente
                    val resistencia = String.format("%.2f", resultado)
                    txvResultado.text = getString(R.string.resultado_resistencia, resistencia.toString())
                }
            } else if (resistenciaTexto.isNotBlank() && correnteTexto.isNotBlank() && tensaoTexto.isBlank()) {
                val resistencia = resistenciaTexto.toDoubleOrNull()
                val corrente = correnteTexto.toDoubleOrNull()

                if (resistencia == null || corrente == null) {
                    Toast.makeText(this, getString(R.string.erro_valor_invalido_ohm), Toast.LENGTH_SHORT).show()
                } else {
                    val resultado = resistencia * corrente
                    val tensao = String.format("%.2f", resultado    )
                    txvResultado.text = getString(R.string.resultado_tensao, tensao.toString())
                }
            }
        }
    }
}