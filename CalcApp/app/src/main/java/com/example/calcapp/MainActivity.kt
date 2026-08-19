package com.example.calcapp

import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.widget.Button

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
        val edtValor1 = findViewById<EditText>(R.id.edt_valor1)
        val edtValor2 = findViewById<EditText>(R.id.edt_valor2)
        val soma = findViewById<Button>(R.id.btn_soma)
        val subtracao = findViewById<Button>(R.id.btn_subtracao)
        val multiplicacao = findViewById<Button>(R.id.btn_multiplicacao)
        val divisao = findViewById<Button>(R.id.btn_divisao)
        val txvResultado = findViewById<TextView>(R.id.txt_resultado)

        soma.setOnClickListener {
            val valor1 = edtValor1.text.toString()
            val valor2 = edtValor2.text.toString()
            if (validarCampos(valor1, valor2)) {
                val resultado = valor1.toDouble() + valor2.toDouble()
                txvResultado.text = getString(R.string.resultado_soma, resultado.toString())
                edtValor1.text.clear()
                edtValor2.text.clear()
                Toast.makeText(this, getString(R.string.calculo_realizado), Toast.LENGTH_SHORT)
                    .show()
            }
        }

        subtracao.setOnClickListener {
            val valor1 = edtValor1.text.toString()
            val valor2 = edtValor2.text.toString()
            if (validarCampos(valor1, valor2)) {
                val resultado = valor1.toDouble() - valor2.toDouble()
                txvResultado.text = getString(R.string.resultado_subtracao, resultado.toString())
                edtValor1.text.clear()
                edtValor2.text.clear()
                Toast.makeText(this, getString(R.string.calculo_realizado), Toast.LENGTH_SHORT)
                    .show()
            }
        }

        multiplicacao.setOnClickListener {
            val valor1 = edtValor1.text.toString()
            val valor2 = edtValor2.text.toString()

            if (validarCampos(valor1, valor2)) {
                val resultado = valor1.toDouble() * valor2.toDouble()
                txvResultado.text =
                    getString(R.string.resultado_multiplicacao, resultado.toString())
                edtValor1.text.clear()
                edtValor2.text.clear()
                Toast.makeText(this, getString(R.string.calculo_realizado), Toast.LENGTH_SHORT)
                    .show()
            }
        }

        divisao.setOnClickListener {
            val valor1 = edtValor1.text.toString()
            val valor2 = edtValor2.text.toString()

            if (validarCampos(valor1, valor2)) {
                if (valor2.toDouble() == 0.0) {
                    Toast.makeText(this, "Não é possível dividir por zero!", Toast.LENGTH_SHORT)
                        .show()
                } else {
                    val resultado = valor1.toDouble() / valor2.toDouble()
                    txvResultado.text = getString(R.string.resultado_divisao, resultado.toString())
                    edtValor1.text.clear()
                    edtValor2.text.clear()
                    Toast.makeText(this, getString(R.string.calculo_realizado), Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }

    private fun validarCampos(valor1: String, valor2: String): Boolean {
        if (valor1.isBlank() || valor2.isBlank()) {
            Toast.makeText(this, "Preencha os dois campos!", Toast.LENGTH_SHORT).show()
            return false
        }
        if (valor1.toDoubleOrNull() == null || valor2.toDoubleOrNull() == null) {
            Toast.makeText(this, "Digite apenas números!", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }

}