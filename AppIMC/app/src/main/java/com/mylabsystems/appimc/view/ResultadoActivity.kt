package com.mylabsystems.appimc.view

import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.mylabsystems.appimc.R
import com.mylabsystems.appimc.dao.IMCDAO
import java.util.Locale

class ResultadoActivity : AppCompatActivity(R.layout.activity_resultado_imc) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val imcResultado = IMCDAO.buscar()
        val txvResultado = findViewById<TextView>(R.id.txv_resultado_imc)
        val txvMensagem = findViewById<TextView>(R.id.txv_mensagem)
        val fabVoltar = findViewById<FloatingActionButton>(R.id.fab_voltar)

        txvResultado.text = String.format(Locale.forLanguageTag("pt-BR"), "%.2f", imcResultado.imcTotal)
        txvMensagem.text = imcResultado.mensagem

        fabVoltar.setOnClickListener {
            finish()
        }
    }
}