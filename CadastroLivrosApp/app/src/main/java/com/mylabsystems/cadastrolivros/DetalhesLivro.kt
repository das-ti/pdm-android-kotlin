package com.mylabsystems.cadastrolivros

import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.floatingactionbutton.FloatingActionButton

class DetalhesLivro : AppCompatActivity(R.layout.activity_detalhes_livro) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val txvTituloLivro = findViewById<TextView>(R.id.txv_titulo_livro)
        val txvAutorLivro = findViewById<TextView>(R.id.txv_autor_livro)
        val fabVoltar = findViewById<FloatingActionButton>(R.id.fab_voltar)

        val tituloCadastrado = intent.getStringExtra("titulo_livro")
        val autorCadastrado = intent.getStringExtra("autor_livro")
        txvTituloLivro.text = "Título: $tituloCadastrado"
        txvAutorLivro.text = "Autor: $autorCadastrado"

        fabVoltar.setOnClickListener {
            finish()
        }
    }
}